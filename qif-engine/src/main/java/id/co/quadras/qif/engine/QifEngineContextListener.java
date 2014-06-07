package id.co.quadras.qif.engine;

import com.google.inject.AbstractModule;
import com.irwin13.winwork.basic.scheduler.BasicSchedulerManager;
import com.irwin13.winwork.basic.utilities.WinWorkUtil;
import id.co.quadras.qif.core.model.entity.QifEvent;
import id.co.quadras.qif.engine.guice.GuiceFactory;
import id.co.quadras.qif.engine.guice.module.*;
import id.co.quadras.qif.engine.service.CounterService;
import id.co.quadras.qif.engine.service.EventService;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.LinkedList;
import java.util.List;

/**
 * @author irwin Timestamp : 07/05/2014 17:59
 */
public class QifEngineContextListener implements ServletContextListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(QifEngineContextListener.class);
    public static final long START = System.currentTimeMillis();

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        LOGGER.info("================================================================================================");
        LOGGER.info("=================================== Starting QifDevContextListener ===============================");

        String nodeName = System.getProperty("nodeName");
        LOGGER.info("nodeName = {}", nodeName);
        MDC.put("nodeName", WinWorkUtil.getNodeName());

        LOGGER.info("=== Starting scan Process and Task package ... ===");
        String rootPackageProcess = servletContextEvent.getServletContext().getInitParameter("rootPackage.process");
        LOGGER.info("initParameter rootPackageProcess = {}", rootPackageProcess);

        String rootPackageTask = servletContextEvent.getServletContext().getInitParameter("rootPackage.task");
        LOGGER.info("initParameter rootPackageTask = {}", rootPackageTask);

        TaskRegister.init(rootPackageTask);
        ProcessRegister.init(rootPackageProcess);
        LOGGER.info("=== Starting scan Process and Task package complete ===");

        LOGGER.info("=== Starting Guice ... ===");
        List<AbstractModule> moduleList = new LinkedList<AbstractModule>();
        moduleList.add(new SharedModule());
        moduleList.add(new DaoModule());
        moduleList.add(new ServiceModule());
        moduleList.add(new TaskModule(TaskRegister.getTaskSet()));
        moduleList.add(new ProcessModule(ProcessRegister.getProcessSet()));
        GuiceFactory.setModuleList(moduleList);
        LOGGER.info("=== Starting Guice complete ===");

        EventService eventService = GuiceFactory.getInjector().getInstance(EventService.class);
        QifEvent filter = new QifEvent();
        filter.setActive(Boolean.TRUE);
        List<QifEvent> eventList = eventService.select(filter);

        LOGGER.info("=== Starting Scheduler QifEvent ... ===");
        SchedulerStarter schedulerStarter = GuiceFactory.getInjector().getInstance(SchedulerStarter.class);

        try {
            schedulerStarter.startEvent(eventList);
            schedulerStarter.startInternalScheduler();
        } catch (SchedulerException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
        LOGGER.info("=== Starting Scheduler QifEvent complete ===");

        LOGGER.info("=== Init counter ... ===");
        CounterService counterService = GuiceFactory.getInjector().getInstance(CounterService.class);
        counterService.initCounter(eventList);
        LOGGER.info("=== Init counter complete ===");

        LOGGER.info("=================================== Starting QifDevContextListener complete ===============================");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        LOGGER.info("================================================================================================");
        LOGGER.info("=================================== Shutdown QifDevContextListener ===============================");

        LOGGER.info("=== Shutdown Quartz scheduler ... ===");
        BasicSchedulerManager schedulerManager = GuiceFactory.getInjector().getInstance(BasicSchedulerManager.class);
        try {
            schedulerManager.shutdown(true);
        } catch (SchedulerException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
        LOGGER.info("=== Shutdown Quartz scheduler complete ===");

        LOGGER.info("=================================== Shutdown QifDevContextListener complete ===============================");

    }
}
