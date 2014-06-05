package id.co.quadras.qif.dev;

import com.google.inject.AbstractModule;
import com.irwin13.winwork.basic.scheduler.BasicSchedulerManager;
import com.irwin13.winwork.basic.utilities.WinWorkUtil;
import id.co.quadras.qif.core.model.entity.QifEvent;
import id.co.quadras.qif.dev.guice.GuiceFactory;
import id.co.quadras.qif.dev.guice.module.*;
import id.co.quadras.qif.dev.service.EventService;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.jdbc.pool.DataSource;
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
public class QifDevContextListener implements ServletContextListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(QifDevContextListener.class);
    public static final long START = System.currentTimeMillis();

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        LOGGER.info("================================================================================================");
        LOGGER.info("=================================== Starting QifDevContextListener ===============================");

        String nodeName = System.getProperty("nodeName");
        LOGGER.info("nodeName = {}", nodeName);
        MDC.put("nodeName", WinWorkUtil.getNodeName());

        String rootPackageProcess = servletContextEvent.getServletContext().getInitParameter("rootPackage.process");
        LOGGER.info("initParameter rootPackageProcess = {}", rootPackageProcess);

        String rootPackageTask = servletContextEvent.getServletContext().getInitParameter("rootPackage.task");
        LOGGER.info("initParameter rootPackageTask = {}", rootPackageTask);

        TaskRegister.init(rootPackageTask);
        ProcessRegister.init(rootPackageProcess);

        List<AbstractModule> moduleList = new LinkedList<AbstractModule>();
        moduleList.add(new SharedModule());
        moduleList.add(new DaoModule());
        moduleList.add(new ServiceModule());
        moduleList.add(new TaskModule(TaskRegister.getTaskSet()));
        moduleList.add(new ProcessModule(ProcessRegister.getProcessSet()));
        GuiceFactory.setModuleList(moduleList);

        LOGGER.info("=== Starting Scheduler QifEvent ... ===");
        EventService eventService = GuiceFactory.getInjector().getInstance(EventService.class);
        SchedulerStarter schedulerStarter = GuiceFactory.getInjector().getInstance(SchedulerStarter.class);

        QifEvent filter = new QifEvent();
        filter.setActive(Boolean.TRUE);
        List<QifEvent> eventList = eventService.select(filter);

        try {
            schedulerStarter.startEvent(eventList);
        } catch (SchedulerException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
        LOGGER.info("=== Starting Scheduler QifEvent complete ===");

        LOGGER.info("=== Starting Internal Scheduler ... ===");
        schedulerStarter.startInternalScheduler();
        LOGGER.info("=== Starting Internal Scheduler complete ===");

        // TODO
        // insert or update qif_counter for registered event, process and task
        // for all counter and today counter
        // example : eventA, eventA_01-01-2013, processA, processA_01-01-2013, taskA, taskA_01-01-2013

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

        LOGGER.info("=== Shutdown MyBatis ... ===");
        SqlSessionFactory sqlSessionFactory = GuiceFactory.getInjector().getInstance(SqlSessionFactory.class);
        DataSource dataSource = (DataSource) sqlSessionFactory.getConfiguration().getEnvironment().getDataSource();
        dataSource.close(true); // close all
        LOGGER.info("=== Shutdown MyBatis complete ===");

        LOGGER.info("=================================== Shutdown QifDevContextListener complete ===============================");

    }
}
