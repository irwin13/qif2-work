package id.co.quadras.qif.engine;

import com.irwin13.winwork.basic.scheduler.BasicSchedulerManager;
import id.co.quadras.qif.core.QifProcess;
import id.co.quadras.qif.core.model.entity.QifEvent;
import id.co.quadras.qif.engine.guice.QifGuiceFactory;
import id.co.quadras.qif.engine.jaxb.Qif;
import id.co.quadras.qif.engine.process.DaemonProcess;
import id.co.quadras.qif.engine.service.CounterService;
import id.co.quadras.qif.engine.service.EventService;
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
        QifGuiceFactory.initEngine(qifConfigFile);

        EventService eventService = QifGuiceFactory.getInjector().getInstance(EventService.class);
        QifEvent filter = new QifEvent();
        filter.setActive(Boolean.TRUE);
        List<QifEvent> eventList = eventService.select(filter);

        LOGGER.info("=== Starting Scheduler QifEvent ... ===");
        SchedulerStarter schedulerStarter = QifGuiceFactory.getInjector().getInstance(SchedulerStarter.class);

        try {
            schedulerStarter.startEvent(eventList);
            schedulerStarter.startInternalScheduler(QifGuiceFactory.getInjector().getInstance(Qif.class));
            schedulerStarter.startScheduler();
        } catch (SchedulerException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
        LOGGER.info("=== Starting Scheduler QifEvent complete ===");

        LOGGER.info("=== Init counter ... ===");
        CounterService counterService = QifGuiceFactory.getInjector().getInstance(CounterService.class);
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
        BasicSchedulerManager schedulerManager = QifGuiceFactory.getInjector().getInstance(BasicSchedulerManager.class);
        try {
            schedulerManager.shutdown(true);
        } catch (SchedulerException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
        LOGGER.info("=== Shutdown Quartz scheduler complete ===");

        LOGGER.info("=== Shutdown ExecutorService ... ===");
        ExecutorService executorService = QifGuiceFactory.getInjector().getInstance(ExecutorService.class);
        executorService.shutdown();
        LOGGER.info("=== Shutdown ExecutorService complete ===");

        LOGGER.info("===== Shutdown QifEngineContextListener complete =====");

    }

    public static boolean loopForever() {
        return LOOP_FOREVER;
    }

    private void submitDaemon(List<QifEvent> eventList) {
        ExecutorService executorService = QifGuiceFactory.getInjector().getInstance(ExecutorService.class);

        for (final QifEvent qifEvent : eventList) {
            boolean active = qifEvent.getActiveAcceptMessage();
            if (active) {
                try {
                    QifProcess qifProcess = (QifProcess) QifGuiceFactory.getInjector().getInstance(Class.forName(qifEvent.getQifProcess()));
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
