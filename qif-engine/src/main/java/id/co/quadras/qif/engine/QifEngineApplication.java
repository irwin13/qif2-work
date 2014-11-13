package id.co.quadras.qif.engine;

import com.google.common.base.Strings;
import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.irwin13.winwork.basic.scheduler.BasicSchedulerManager;
import com.irwin13.winwork.basic.utilities.WinWorkUtil;
import id.co.quadras.qif.engine.bundle.QifGuiceBundle;
import id.co.quadras.qif.engine.config.QifConfig;
import id.co.quadras.qif.engine.core.QifProcess;
import id.co.quadras.qif.engine.core.QifUtil;
import id.co.quadras.qif.engine.guice.QifGuice;
import id.co.quadras.qif.engine.healthcheck.DbRepoHealthCheck;
import id.co.quadras.qif.engine.process.DaemonProcess;
import id.co.quadras.qif.engine.service.CounterService;
import id.co.quadras.qif.engine.service.EventService;
import id.co.quadras.qif.engine.service.QueueDrainer;
import id.co.quadras.qif.engine.service.app.AppSettingService;
import id.co.quadras.qif.engine.web.*;
import id.co.quadras.qif.engine.web.servlet.EventDispatcherServlet;
import id.co.quadras.qif.model.entity.QifEvent;
import id.co.quadras.qif.model.entity.QifEventProperty;
import id.co.quadras.qif.model.vo.event.EventJms;
import io.dropwizard.Application;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @author irwin Timestamp : 22/08/2014 16:31
 */
public abstract class QifEngineApplication extends Application<QifConfig> {

    public static final long START = System.currentTimeMillis();

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private static boolean ACTIVE = true;
    private static Injector injector;
    private static HazelcastInstance hazelcastInstance;

    private static int DEFAULT_DAEMON_THREAD_COUNT = 1;

    protected abstract void initializeApplication(Bootstrap<QifConfig> qifConfigBootstrap);
    protected abstract void runApplication(QifConfig qifConfig, Environment environment);
    protected abstract List<AbstractModule> applicationModule();

    @Override
    public void initialize(Bootstrap<QifConfig> bootstrap) {

        String nodeName = System.getProperty("nodeName");
        LOGGER.info("nodeName = {}", nodeName);
        MDC.put("nodeName", WinWorkUtil.getNodeName());

        LOGGER.info("Initialize Guice ...");
        QifGuiceBundle qifGuiceBundle = new QifGuiceBundle(applicationModule());
        bootstrap.addBundle(qifGuiceBundle);

        initializeApplication(bootstrap);
    }

    @Override
    public void run(QifConfig qifConfig, Environment environment) throws Exception {
        LOGGER.info("===== QifEngineApplication run =====");

        LOGGER.info("===== Start Hazelcast =====");
        Config config = new Config();
        hazelcastInstance = Hazelcast.newHazelcastInstance(config);
        LOGGER.info("===== Start Hazelcast complete =====");

        injector = QifGuice.getInjector();

        EventService eventService = injector.getInstance(EventService.class);
        QifEvent filter = new QifEvent();
        filter.setActive(Boolean.TRUE);
        List<QifEvent> eventList = eventService.select(filter);

        LOGGER.info("=== Starting Scheduler QifEvent ... ===");
        SchedulerStarter schedulerStarter = injector.getInstance(SchedulerStarter.class);

        try {
            schedulerStarter.startEvent(eventList);
            schedulerStarter.startInternalTimer(qifConfig);
            schedulerStarter.startScheduler();
        } catch (SchedulerException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
        LOGGER.info("=== Starting Scheduler QifEvent complete ===");

        LOGGER.info("=== Init counter ... ===");
        CounterService counterService = injector.getInstance(CounterService.class);
        counterService.initCounter(eventList);
        LOGGER.info("=== Init counter complete ===");

        LOGGER.info("=== Register servlet ... ===");
        environment.getApplicationContext().addServlet(EventDispatcherServlet.class, "/http-event/*");
        LOGGER.info("=== Register servlet complete ===");

        LOGGER.info("=== Register resources ... ===");
        environment.jersey().register(AppStatusResource.class);
        environment.jersey().register(AdapterResource.class);
        environment.jersey().register(EventResource.class);
        environment.jersey().register(ProcessResource.class);
        environment.jersey().register(TaskResource.class);
        LOGGER.info("=== Register resources complete ===");

        LOGGER.info("=== Submit daemon ... ===");
        submitDaemon(eventList);
        LOGGER.info("=== Submit daemon complete ===");

        environment.lifecycle().manage(new Managed() {
            @Override
            public void start() throws Exception {}

            @Override
            public void stop() throws Exception {
                LOGGER.info("===== Shutdown QifEngineApplication =====");

                LOGGER.info("=== Shutdown loop forever ... ===");
                ACTIVE = false;
                LOGGER.info("=== Shutdown loop forever complete ===");

                LOGGER.info("=== Shutdown Quartz scheduler ... ===");
                BasicSchedulerManager schedulerManager = injector.getInstance(BasicSchedulerManager.class);
                try {
                    schedulerManager.shutdown(true);
                } catch (SchedulerException e) {
                    LOGGER.error(e.getLocalizedMessage(), e);
                }
                LOGGER.info("=== Shutdown Quartz scheduler complete ===");

                LOGGER.info("=== Shutdown ExecutorService ... ===");
                ExecutorService executorService = injector.getInstance(ExecutorService.class);
                executorService.shutdown();
                LOGGER.info("=== Shutdown ExecutorService complete ===");

                LOGGER.info("=== Draining queue ... ===");
                QueueDrainer queueDrainer = injector.getInstance(QueueDrainer.class);
                queueDrainer.drainQueue();
                LOGGER.info("=== Draining queue complete ===");

                // sleep for 15 seconds to let all process done before shutdown hazelcast
                Thread.sleep(15000);

                LOGGER.info("=== Shutdown Hazelcast ... ===");
                hazelcastInstance.shutdown();
                LOGGER.info("=== Shutdown Hazelcast complete ===");

                LOGGER.info("===== Shutdown QifEngineApplication complete =====");
            }
        });

        AppSettingService appSettingService = injector.getInstance(AppSettingService.class);
        environment.healthChecks().register("databaseRepositoryHealthCheck", new DbRepoHealthCheck(appSettingService));

        runApplication(qifConfig, environment);
        LOGGER.info("===== QifEngineApplication run complete =====");

    }

    public static boolean isActive() {
        return ACTIVE;
    }

    public static HazelcastInstance getHazelcastInstance() {
        return hazelcastInstance;
    }

    private void submitDaemon(List<QifEvent> eventList) {
        for (final QifEvent qifEvent : eventList) {
            boolean active = qifEvent.getActiveAcceptMessage();
            if (active) {
                try {
                    QifProcess qifProcess = (QifProcess) injector.getInstance(Class.forName(qifEvent.getQifProcess()));
                    if (qifProcess instanceof DaemonProcess) {
                        int threadCount = DEFAULT_DAEMON_THREAD_COUNT;
                        QifEventProperty threadCountProp = QifUtil.getEventProperty(qifEvent, EventJms.THREAD_COUNT.getName());
                        if (threadCountProp != null) {
                            String value = threadCountProp.getPropertyValue();
                            if (!Strings.isNullOrEmpty(value)) {
                                try {
                                    threadCount = Integer.valueOf(value);
                                } catch (NumberFormatException e) {
                                    LOGGER.error("Error parsing integer in property 'thread_count' for Event '{}'", qifEvent.getName());
                                }
                            }
                        }

                        for (int i = 0; i < threadCount; i++) {
                            Runnable runnable = qifProcess.createDaemon(qifEvent);
                            if (runnable != null) {
                                Thread thread = new Thread(runnable);
                                thread.setName(qifEvent.getName() + "_" + i);
                                thread.start();
                                LOGGER.info("Submit thread daemon {} for event {} with process {}",
                                        new Object[]{i, qifEvent.getName(), qifProcess.activityName()});
                            } else {
                                LOGGER.error("Unexpected null Runnable for event {} with process {}", qifEvent.getName(), qifProcess.activityName());
                            }
                        }
                    }
                } catch (Exception e) {
                    LOGGER.error(e.getLocalizedMessage(), e);
                }
            }
        }
    }
}
