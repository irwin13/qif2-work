package id.co.quadras.qif.engine;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.irwin13.winwork.basic.scheduler.BasicSchedulerManager;
import com.irwin13.winwork.basic.utilities.WinWorkUtil;
import id.co.quadras.qif.engine.bundle.GuiceBundle;
import id.co.quadras.qif.engine.config.QifConfig;
import id.co.quadras.qif.engine.core.QifProcess;
import id.co.quadras.qif.engine.guice.QifGuice;
import id.co.quadras.qif.engine.process.DaemonProcess;
import id.co.quadras.qif.engine.service.CounterService;
import id.co.quadras.qif.engine.service.EventService;
import id.co.quadras.qif.engine.web.servlet.*;
import id.co.quadras.qif.model.entity.QifEvent;
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

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    public static final long START = System.currentTimeMillis();
    private static boolean LOOP_FOREVER = true;
    private static Injector injector;

    protected abstract void initializeApplication(Bootstrap<QifConfig> qifConfigBootstrap);
    protected abstract void runApplication(QifConfig qifConfig, Environment environment);
    protected abstract List<AbstractModule> applicationModule();

    @Override
    public void initialize(Bootstrap<QifConfig> bootstrap) {

        String nodeName = System.getProperty("nodeName");
        LOGGER.info("nodeName = {}", nodeName);
        MDC.put("nodeName", WinWorkUtil.getNodeName());

        LOGGER.info("Initialize Guice ...");
        GuiceBundle guiceBundle = new GuiceBundle(applicationModule());
        bootstrap.addBundle(guiceBundle);

        initializeApplication(bootstrap);
    }

    @Override
    public void run(QifConfig qifConfig, Environment environment) throws Exception {
        LOGGER.info("===== QifEngineApplication run =====");
        injector = QifGuice.getInjector();

        EventService eventService = injector.getInstance(EventService.class);
        QifEvent filter = new QifEvent();
        filter.setActive(Boolean.TRUE);
        List<QifEvent> eventList = eventService.select(filter);

        LOGGER.info("=== Starting Scheduler QifEvent ... ===");
        SchedulerStarter schedulerStarter = injector.getInstance(SchedulerStarter.class);

        try {
            schedulerStarter.startEvent(eventList);
            schedulerStarter.startInternalScheduler(qifConfig);
            schedulerStarter.startScheduler();
        } catch (SchedulerException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
        LOGGER.info("=== Starting Scheduler QifEvent complete ===");

        LOGGER.info("=== Init counter ... ===");
        CounterService counterService = injector.getInstance(CounterService.class);
        counterService.initCounter(eventList);
        LOGGER.info("=== Init counter complete ===");

        LOGGER.info("=== Register servlets ... ===");
        environment.getApplicationContext().addServlet(EventDispatcherServlet.class, "/http-event/*");
        environment.getApplicationContext().addServlet(AppStatusServlet.class, "/app-status");
        environment.getApplicationContext().addServlet(EventApiServlet.class, "/event-api");
        environment.getApplicationContext().addServlet(ProcessApiServlet.class, "/process-api");
        environment.getApplicationContext().addServlet(TaskApiServlet.class, "/task-api");
        LOGGER.info("=== Register servlets complete ===");

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
                LOOP_FOREVER = false;
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

                LOGGER.info("===== Shutdown QifEngineApplication complete =====");
            }
        });

        runApplication(qifConfig, environment);
        LOGGER.info("===== QifEngineApplication run complete =====");

    }

    public static Injector getInjector() {
        return injector;
    }

    public static boolean loopForever() {
        return LOOP_FOREVER;
    }

    private void submitDaemon(List<QifEvent> eventList) {
        ExecutorService executorService = injector.getInstance(ExecutorService.class);

        for (final QifEvent qifEvent : eventList) {
            boolean active = qifEvent.getActiveAcceptMessage();
            if (active) {
                try {
                    QifProcess qifProcess = (QifProcess) injector.getInstance(Class.forName(qifEvent.getQifProcess()));
                    if (qifProcess instanceof DaemonProcess) {
                        Runnable runnable = qifProcess.createDaemon(qifEvent);
                        if (runnable != null) {
                            executorService.submit(runnable);
                            LOGGER.info("Submit daemon for event {} with process {}", qifEvent.getName(), qifProcess.activityName());
                        } else {
                            LOGGER.error("Unexpected null Runnable for event {} with process {}", qifEvent.getName(), qifProcess.activityName());
                        }
                    }
                } catch (Exception e) {
                    LOGGER.error(e.getLocalizedMessage(), e);
                }
            }
        }
    }
}