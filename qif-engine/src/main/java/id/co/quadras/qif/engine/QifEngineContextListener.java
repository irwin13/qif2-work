package id.co.quadras.qif.engine;

import com.irwin13.winwork.basic.scheduler.BasicSchedulerManager;
import id.co.quadras.qif.core.model.entity.QifEvent;
import id.co.quadras.qif.engine.guice.EngineFactory;
import id.co.quadras.qif.engine.jaxb.Qif;
import id.co.quadras.qif.engine.service.CounterService;
import id.co.quadras.qif.engine.service.EventService;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

/**
 * @author irwin Timestamp : 07/05/2014 17:59
 */
public class QifEngineContextListener implements ServletContextListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(QifEngineContextListener.class);
    public static final long START = System.currentTimeMillis();

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        LOGGER.info("=========================");
        LOGGER.info("===== Starting QifEngineContextListener =====");

        String qifConfigFile = servletContextEvent.getServletContext().getInitParameter("qifConfigFile");
        LOGGER.info("QIF Configuration file = {}", qifConfigFile);
        EngineFactory.initEngine(qifConfigFile);

        EventService eventService = EngineFactory.getInjector().getInstance(EventService.class);
        QifEvent filter = new QifEvent();
        filter.setActive(Boolean.TRUE);
        List<QifEvent> eventList = eventService.select(filter);

        LOGGER.info("=== Starting Scheduler QifEvent ... ===");
        SchedulerStarter schedulerStarter = EngineFactory.getInjector().getInstance(SchedulerStarter.class);

        try {
            schedulerStarter.startEvent(eventList);
            schedulerStarter.startInternalScheduler(EngineFactory.getInjector().getInstance(Qif.class));
        } catch (SchedulerException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
        LOGGER.info("=== Starting Scheduler QifEvent complete ===");

        LOGGER.info("=== Init counter ... ===");
        CounterService counterService = EngineFactory.getInjector().getInstance(CounterService.class);
        counterService.initCounter(eventList);
        LOGGER.info("=== Init counter complete ===");

        LOGGER.info("===== Starting QifEngineContextListener complete =====");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        LOGGER.info("=========================");
        LOGGER.info("===== Shutdown QifEngineContextListener =====");

        LOGGER.info("=== Shutdown Quartz scheduler ... ===");
        BasicSchedulerManager schedulerManager = EngineFactory.getInjector().getInstance(BasicSchedulerManager.class);
        try {
            schedulerManager.shutdown(true);
        } catch (SchedulerException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
        LOGGER.info("=== Shutdown Quartz scheduler complete ===");

        LOGGER.info("===== Shutdown QifEngineContextListener complete =====");

    }
}
