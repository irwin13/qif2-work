package id.co.quadras.qif.dev;

import com.irwin13.winwork.basic.scheduler.BasicSchedulerManager;
import com.irwin13.winwork.basic.utilities.WinWorkUtil;
import id.co.quadras.qif.dev.dao.EventDao;
import id.co.quadras.qif.dev.guice.GuiceFactory;
import id.co.quadras.qif.dev.service.EventService;
import id.co.quadras.qif.model.entity.QifEvent;
import id.co.quadras.qif.model.vo.event.EventType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
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

        EventService eventService = GuiceFactory.getInjector().getInstance(EventService.class);
        SchedulerStarter schedulerStarter = GuiceFactory.getInjector().getInstance(SchedulerStarter.class);

        QifEvent filterInterval = new QifEvent();
        filterInterval.setEventType(EventType.SCHEDULER_INTERVAL.getName());
        List<QifEvent> eventIntervalList = eventService.select(filterInterval);

        QifEvent filterCron = new QifEvent();
        filterCron.setEventType(EventType.SCHEDULER_CRON.getName());
        List<QifEvent> eventCronList = eventService.select(filterCron);

        try {
            schedulerStarter.startEvent(eventIntervalList);
            schedulerStarter.startEvent(eventCronList);
        } catch (SchedulerException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }

        schedulerStarter.startInternalScheduler();

        // TODO
        // insert QifCounter by process, by task, by date for the next day
        // if today date is 01-01-2013 then insert data for 02-01-2013
        // ex : processA_02-01-2013
        // taskA_02-01-2013
        LOGGER.info("=================================== Starting QifDevContextListener complete ===============================");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        LOGGER.info("================================================================================================");
        LOGGER.info("=================================== Shutdown QifDevContextListener ===============================");

        BasicSchedulerManager schedulerManager = GuiceFactory.getInjector().getInstance(BasicSchedulerManager.class);
        try {
            schedulerManager.shutdown(true);
        } catch (SchedulerException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }

        SqlSessionFactory sqlSessionFactory = GuiceFactory.getInjector().getInstance(SqlSessionFactory.class);
        DataSource dataSource = (DataSource) sqlSessionFactory.getConfiguration().getEnvironment().getDataSource();
        dataSource.close(true); // close all

        LOGGER.info("=================================== Shutdown QifDevContextListener complete ===============================");

    }
}
