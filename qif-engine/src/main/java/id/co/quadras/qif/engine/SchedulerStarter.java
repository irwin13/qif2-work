package id.co.quadras.qif.engine;

import com.google.inject.Inject;
import com.irwin13.winwork.basic.scheduler.BasicSchedulerManager;
import id.co.quadras.qif.core.QifConstants;
import id.co.quadras.qif.core.QifUtil;
import id.co.quadras.qif.core.model.entity.QifEvent;
import id.co.quadras.qif.core.model.entity.QifEventProperty;
import id.co.quadras.qif.core.model.vo.event.EventType;
import id.co.quadras.qif.core.model.vo.event.SchedulerCron;
import id.co.quadras.qif.core.model.vo.event.SchedulerInterval;
import id.co.quadras.qif.engine.guice.GuiceFactory;
import id.co.quadras.qif.engine.job.QifEventConcurrentJob;
import id.co.quadras.qif.engine.job.QifEventSingleInstanceJob;
import id.co.quadras.qif.engine.job.timertask.*;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

/**
 * @author irwin Timestamp : 12/05/2014 17:47
 */
public final class SchedulerStarter {

    private static final long LOG_PERSIST_DELAY = 30000;
    private static final long DEFAULT_LOG_PERSIST_INTERVAL = 5000;

    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerStarter.class);
    private final BasicSchedulerManager schedulerManager;

    @Inject
    public SchedulerStarter(BasicSchedulerManager schedulerManager) {
        this.schedulerManager = schedulerManager;
    }

    public void startEvent(List<QifEvent> qifEventList) throws SchedulerException {
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

            if (EventType.SCHEDULER_INTERVAL.getName().equalsIgnoreCase(qifEvent.getEventType())) {
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
            } else if (EventType.SCHEDULER_CRON.getName().equalsIgnoreCase(qifEvent.getEventType())) {
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
            }

            if (!schedulerManager.isJobAlreadyExists(jobKey) && jobDetail != null) {
                schedulerManager.add(jobDetail, trigger);
            } else if (jobDetail != null) {
                LOGGER.debug("JobKey {} already exists, try to reschedule", jobKey);
                schedulerManager.reschedule(triggerKey, trigger);
            }
        }

        if (!schedulerManager.isStarted()) {
            schedulerManager.start();
        }
    }

    public void startInternalScheduler() {
        EventLogPersist eventLogPersist = GuiceFactory.getInjector().getInstance(EventLogPersist.class);
        new Timer().schedule(eventLogPersist, LOG_PERSIST_DELAY, DEFAULT_LOG_PERSIST_INTERVAL);

        EventLogMessagePersist eventLogMessagePersist =
                GuiceFactory.getInjector().getInstance(EventLogMessagePersist.class);
        new Timer().schedule(eventLogMessagePersist, LOG_PERSIST_DELAY, DEFAULT_LOG_PERSIST_INTERVAL);

        ActivityLogPersist activityLogPersist = GuiceFactory.getInjector().getInstance(ActivityLogPersist.class);
        new Timer().schedule(activityLogPersist, LOG_PERSIST_DELAY, DEFAULT_LOG_PERSIST_INTERVAL);

        ActivityLogDataPersist activityLogDataPersist =
                GuiceFactory.getInjector().getInstance(ActivityLogDataPersist.class);
        new Timer().schedule(activityLogDataPersist, LOG_PERSIST_DELAY, DEFAULT_LOG_PERSIST_INTERVAL);

        ActivityLogInputMessagePersist activityLogInputMessagePersist =
                GuiceFactory.getInjector().getInstance(ActivityLogInputMessagePersist.class);
        new Timer().schedule(activityLogInputMessagePersist, LOG_PERSIST_DELAY, DEFAULT_LOG_PERSIST_INTERVAL);

        ActivityLogOutputMessagePersist activityLogOutputMessagePersist =
                GuiceFactory.getInjector().getInstance(ActivityLogOutputMessagePersist.class);
        new Timer().schedule(activityLogOutputMessagePersist, LOG_PERSIST_DELAY, DEFAULT_LOG_PERSIST_INTERVAL);

        CounterUpdate counterUpdate = GuiceFactory.getInjector().getInstance(CounterUpdate.class);
        new Timer().schedule(counterUpdate, LOG_PERSIST_DELAY, DEFAULT_LOG_PERSIST_INTERVAL);
    }
}
