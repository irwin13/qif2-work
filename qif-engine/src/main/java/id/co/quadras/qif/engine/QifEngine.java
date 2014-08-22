package id.co.quadras.qif.engine;

import com.irwin13.winwork.basic.scheduler.BasicSchedulerManager;
import id.co.quadras.qif.engine.config.QifConfig;
import id.co.quadras.qif.engine.core.QifProcess;
import id.co.quadras.qif.engine.guice.QifGuice;
import id.co.quadras.qif.engine.process.DaemonProcess;
import id.co.quadras.qif.engine.service.CounterService;
import id.co.quadras.qif.engine.service.EventService;
import id.co.quadras.qif.model.entity.QifEvent;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @author irwin Timestamp : 07/05/2014 17:59
 */
@Deprecated
public class QifEngine implements ServletContextListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(QifEngine.class);
    public static final long START = System.currentTimeMillis();
    private static boolean LOOP_FOREVER = true;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        LOGGER.info("=========================");
        LOGGER.info("===== Starting QifEngineContextListener =====");

        String qifConfigFile = servletContextEvent.getServletContext().getInitParameter("qifConfigFile");
        LOGGER.info("QIF Configuration file = {}", qifConfigFile);
//        QifGuice.initEngine(qifConfigFile);

        EventService eventService = QifGuice.getInjector().getInstance(EventService.class);
        QifEvent filter = new QifEvent();
        filter.setActive(Boolean.TRUE);
        List<QifEvent> eventList = eventService.select(filter);

        LOGGER.info("=== Starting Scheduler QifEvent ... ===");
        SchedulerStarter schedulerStarter = QifEngineApplication.getInjector().getInstance(SchedulerStarter.class);

        try {
            schedulerStarter.startEvent(eventList);
            schedulerStarter.startInternalScheduler(QifEngineApplication.getInjector().getInstance(QifConfig.class));
            schedulerStarter.startScheduler();
        } catch (SchedulerException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
        LOGGER.info("=== Starting Scheduler QifEvent complete ===");

        LOGGER.info("=== Init counter ... ===");
        CounterService counterService = QifEngineApplication.getInjector().getInstance(CounterService.class);
        counterService.initCounter(eventList);
        LOGGER.info("=== Init counter complete ===");

        LOGGER.info("=== Submit daemon ... ===");
        submitDaemon(eventList);
        LOGGER.info("=== Submit daemon complete ===");

        LOGGER.info("===== Starting QifEngineContextListener complete =====");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        LOGGER.info("=========================");
        LOGGER.info("===== Shutdown QifEngineContextListener =====");

        LOGGER.info("=== Shutdown loop forever ... ===");
        LOOP_FOREVER = false;
        LOGGER.info("=== Shutdown loop forever complete ===");

        LOGGER.info("=== Shutdown Quartz scheduler ... ===");
        BasicSchedulerManager schedulerManager = QifEngineApplication.getInjector().getInstance(BasicSchedulerManager.class);
        try {
            schedulerManager.shutdown(true);
        } catch (SchedulerException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
        LOGGER.info("=== Shutdown Quartz scheduler complete ===");

        LOGGER.info("=== Shutdown ExecutorService ... ===");
        ExecutorService executorService = QifEngineApplication.getInjector().getInstance(ExecutorService.class);
        executorService.shutdown();
        LOGGER.info("=== Shutdown ExecutorService complete ===");

        LOGGER.info("===== Shutdown QifEngineContextListener complete =====");

    }

    public static boolean loopForever() {
        return LOOP_FOREVER;
    }

    private void submitDaemon(List<QifEvent> eventList) {
        ExecutorService executorService = QifEngineApplication.getInjector().getInstance(ExecutorService.class);

        for (final QifEvent qifEvent : eventList) {
            boolean active = qifEvent.getActiveAcceptMessage();
            if (active) {
                try {
                    QifProcess qifProcess = (QifProcess) QifEngineApplication.getInjector().getInstance(Class.forName(qifEvent.getQifProcess()));
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
