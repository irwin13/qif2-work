package id.co.quadras.qif.engine;

import com.google.inject.Inject;
import com.irwin13.winwork.basic.scheduler.BasicSchedulerManager;
import id.co.quadras.qif.engine.config.QifConfig;
import id.co.quadras.qif.engine.core.QifConstants;
import id.co.quadras.qif.engine.core.QifUtil;
import id.co.quadras.qif.engine.guice.QifGuice;
import id.co.quadras.qif.engine.job.QifEventConcurrentJob;
import id.co.quadras.qif.engine.job.QifEventSingleInstanceJob;
import id.co.quadras.qif.engine.job.internal.*;
import id.co.quadras.qif.engine.json.QifJsonParser;
import id.co.quadras.qif.model.entity.QifEvent;
import id.co.quadras.qif.model.entity.QifEventProperty;
import id.co.quadras.qif.model.vo.event.EventType;
import id.co.quadras.qif.model.vo.event.SchedulerCron;
import id.co.quadras.qif.model.vo.event.SchedulerInterval;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
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

    public void startScheduler() throws SchedulerException {
        schedulerManager.start();
    }

    public void startEvent(List<QifEvent> qifEventList) throws SchedulerException {
        LOGGER.info("=====================================");
        LOGGER.info("Configure BP Listener with type SCHEDULER ");

        for (QifEvent qifEvent : qifEventList) {
            try {
                LOGGER.debug(QifGuice.getInjector().getInstance(QifJsonParser.class).parseToString(false, qifEvent));
            } catch (IOException e) {
                LOGGER.error(e.getLocalizedMessage(), e);
            }
            if (qifEvent.getEventType().equalsIgnoreCase(EventType.SCHEDULER_CRON.getName()) ||
                    qifEvent.getEventType().equalsIgnoreCase(EventType.SCHEDULER_INTERVAL.getName())) {

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
        }
    }

    public void startInternalScheduler(QifConfig qifConfig) throws SchedulerException {
        LOGGER.info("Start Internal Scheduler ");

        // Counter update
        if (!schedulerManager.isJobAlreadyExists(schedulerManager.createJobKey(CounterUpdate.class.getName()))) {
            schedulerManager.add(
                    schedulerManager.createJobDetail(CounterUpdate.class,
                            schedulerManager.createJobKey(CounterUpdate.class.getName()),
                            null),
                    schedulerManager.createIntervalTrigger(schedulerManager.createTriggerKey(CounterUpdate.class.getName()),
                            Integer.valueOf(qifConfig.getBatchConfig().getCounterUpdateInterval()),
                            false));
        }

        // event log
        if (!schedulerManager.isJobAlreadyExists(schedulerManager.createJobKey(EventLogMsgPersist.class.getName()))) {
            schedulerManager.add(
                    schedulerManager.createJobDetail(EventLogMsgPersist.class,
                            schedulerManager.createJobKey(EventLogMsgPersist.class.getName()),
                            null),
                    schedulerManager.createIntervalTrigger(schedulerManager.createTriggerKey(EventLogMsgPersist.class.getName()),
                            Integer.valueOf(qifConfig.getBatchConfig().getEventLogMsgPersistInterval()),
                            false));
        }

        if (!schedulerManager.isJobAlreadyExists(schedulerManager.createJobKey(EventLogPersist.class.getName()))) {
            schedulerManager.add(
                    schedulerManager.createJobDetail(EventLogPersist.class,
                            schedulerManager.createJobKey(EventLogPersist.class.getName()),
                            null),
                    schedulerManager.createIntervalTrigger(schedulerManager.createTriggerKey(EventLogPersist.class.getName()),
                            Integer.valueOf(qifConfig.getBatchConfig().getEventLogPersistInterval()),
                            false));
        }

        if (!schedulerManager.isJobAlreadyExists(schedulerManager.createJobKey(ActivityLogPersist.class.getName()))) {
            // activity log
            schedulerManager.add(
                    schedulerManager.createJobDetail(ActivityLogPersist.class,
                            schedulerManager.createJobKey(ActivityLogPersist.class.getName()),
                            null),
                    schedulerManager.createIntervalTrigger(schedulerManager.createTriggerKey(ActivityLogPersist.class.getName()),
                            Integer.valueOf(qifConfig.getBatchConfig().getActivityLogPersistInterval()),
                            false));
        }

        if (!schedulerManager.isJobAlreadyExists(schedulerManager.createJobKey(ActivityLogDataPersist.class.getName()))) {
            schedulerManager.add(
                    schedulerManager.createJobDetail(ActivityLogDataPersist.class,
                            schedulerManager.createJobKey(ActivityLogDataPersist.class.getName()),
                            null),
                    schedulerManager.createIntervalTrigger(schedulerManager.createTriggerKey(ActivityLogDataPersist.class.getName()),
                            Integer.valueOf(qifConfig.getBatchConfig().getActivityLogDataPersistInterval()),
                            false));
        }

        if (!schedulerManager.isJobAlreadyExists(schedulerManager.createJobKey(ActivityLogInputMsgPersist.class.getName()))) {
            schedulerManager.add(
                    schedulerManager.createJobDetail(ActivityLogInputMsgPersist.class,
                            schedulerManager.createJobKey(ActivityLogInputMsgPersist.class.getName()),
                            null),
                    schedulerManager.createIntervalTrigger(schedulerManager.createTriggerKey(ActivityLogInputMsgPersist.class.getName()),
                            Integer.valueOf(qifConfig.getBatchConfig().getActivityLogInputMsgPersistInterval()),
                            false));
        }

        if (!schedulerManager.isJobAlreadyExists(schedulerManager.createJobKey(ActivityLogOutputMsgPersist.class.getName()))) {
            schedulerManager.add(
                    schedulerManager.createJobDetail(ActivityLogOutputMsgPersist.class,
                            schedulerManager.createJobKey(ActivityLogOutputMsgPersist.class.getName()),
                            null),
                    schedulerManager.createIntervalTrigger(schedulerManager.createTriggerKey(ActivityLogOutputMsgPersist.class.getName()),
                            Integer.valueOf(qifConfig.getBatchConfig().getActivityLogOutputMsgPersistInterval()),
                            false));
        }

        LOGGER.info("Start Internal Scheduler complete");
    }
}
