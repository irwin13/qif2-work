package id.co.quadras.qif.dev;

import com.google.inject.Inject;
import com.irwin13.winwork.basic.exception.WinWorkException;
import com.irwin13.winwork.basic.scheduler.BasicSchedulerManager;
import id.co.quadras.qif.QifConstants;
import id.co.quadras.qif.QifUtil;
import id.co.quadras.qif.dev.job.QifEventConcurrentJob;
import id.co.quadras.qif.dev.job.QifEventSingleInstanceJob;
import id.co.quadras.qif.model.entity.QifEvent;
import id.co.quadras.qif.model.entity.QifEventProperty;
import id.co.quadras.qif.model.vo.event.EventType;
import id.co.quadras.qif.model.vo.event.SchedulerCron;
import id.co.quadras.qif.model.vo.event.SchedulerInterval;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author irwin Timestamp : 12/05/2014 17:47
 */
public final class SchedulerStarter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerStarter.class);

    private final BasicSchedulerManager schedulerManager;

    @Inject
    public SchedulerStarter(BasicSchedulerManager schedulerManager) {
        this.schedulerManager = schedulerManager;
    }

    public void start(List<QifEvent> qifEventList) throws SchedulerException {
        LOGGER.info("=====================================");
        LOGGER.info("Configure BP Listener with type SCHEDULER ");

        for (QifEvent qifEvent : qifEventList) {

            QifEventProperty concurrentExecutionProperty = QifUtil.getEventProperty(qifEvent,
                    SchedulerInterval.CONCURRENT_EXECUTION.getName());

            Map<String, Object> jobDataMap = new HashMap<String, Object>();
            jobDataMap.put(QifConstants.QIF_EVENT_ID, qifEvent.getId());

            JobKey jobKey = schedulerManager.createJobKey(qifEvent.getId());
            JobDetail jobDetail;

            if (Boolean.valueOf(concurrentExecutionProperty.getPropertyValue())) {
                jobDetail = schedulerManager.createJobDetail(QifEventConcurrentJob.class, jobKey, jobDataMap);
            } else {
                jobDetail = schedulerManager.createJobDetail(QifEventSingleInstanceJob.class, jobKey, jobDataMap);
            }

            TriggerKey triggerKey = schedulerManager.createTriggerKey(qifEvent.getId());
            Trigger trigger = null;

            if (EventType.SCHEDULER_INTERVAL.getName().equalsIgnoreCase(qifEvent.getEventInterface())) {
                QifEventProperty intervalProperty = QifUtil.getEventProperty(qifEvent,
                        SchedulerInterval.INTERVAL.getName());
                if (intervalProperty != null) {
                    int interval = Integer.valueOf(intervalProperty.getPropertyValue());
                    trigger = schedulerManager.createIntervalTrigger(triggerKey, interval, false);
                    LOGGER.info("Register QifEvent '{}' with interval '{} seconds' ", qifEvent.getName(), interval);
                } else {
                    LOGGER.error("FATAL : Missing QifEvent Property with property key = 'interval' for QifEvent {}",
                            qifEvent.getName());
                }
            } else if (EventType.SCHEDULER_CRON.getName().equalsIgnoreCase(qifEvent.getEventInterface())) {
                QifEventProperty cronProperty = QifUtil.getEventProperty(qifEvent,
                        SchedulerCron.CRON_EXPRESSION.getName());
                if (cronProperty != null) {
                    String cronExpression = cronProperty.getPropertyValue();
                    trigger = schedulerManager.createCronTrigger(triggerKey, cronExpression);
                    LOGGER.info("Register QifEvent '{}' with cronExpression '{}'", qifEvent.getName(),
                            cronExpression);
                } else {
                    LOGGER.error("FATAL : Missing Listener Property with property key = 'cronExpression' for QifEvent {}",
                            qifEvent.getName());
                }
            } else {
            throw new WinWorkException("FATAL : Property listenerInterface on QifEvent " + qifEvent.getName()
                        + " is not scheduler_interval or scheduler_cron");
            }

            if (!schedulerManager.isJobAlreadyExists(jobKey) && jobDetail != null) {
                schedulerManager.add(jobDetail, trigger);
            } else if (jobDetail != null) {
                LOGGER.debug("JobKey {} already exists, try to reschedule", jobKey);
                schedulerManager.reschedule(triggerKey, trigger);
            }
        }
        schedulerManager.start();
    }
}
