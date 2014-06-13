package id.co.quadras.qif.engine;

import com.google.inject.Inject;
import com.irwin13.winwork.basic.scheduler.BasicSchedulerManager;
import id.co.quadras.qif.core.QifConstants;
import id.co.quadras.qif.core.QifUtil;
import id.co.quadras.qif.core.helper.JsonParser;
import id.co.quadras.qif.core.model.entity.QifEvent;
import id.co.quadras.qif.core.model.entity.QifEventProperty;
import id.co.quadras.qif.core.model.vo.event.EventType;
import id.co.quadras.qif.core.model.vo.event.SchedulerCron;
import id.co.quadras.qif.core.model.vo.event.SchedulerInterval;
import id.co.quadras.qif.engine.guice.EngineFactory;
import id.co.quadras.qif.engine.jaxb.Qif;
import id.co.quadras.qif.engine.job.QifEventConcurrentJob;
import id.co.quadras.qif.engine.job.QifEventSingleInstanceJob;
import id.co.quadras.qif.engine.job.internal.*;
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

    public void startEvent(List<QifEvent> qifEventList) throws SchedulerException {
        LOGGER.info("=====================================");
        LOGGER.info("Configure BP Listener with type SCHEDULER ");

        for (QifEvent qifEvent : qifEventList) {
            try {
                LOGGER.debug(EngineFactory.getInjector().getInstance(JsonParser.class).parseToString(false, qifEvent));
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

        if (!schedulerManager.isStarted()) {
            schedulerManager.start();
        }
    }

    public void startInternalScheduler(Qif qif) throws SchedulerException {
        // event log
        schedulerManager.add(
                schedulerManager.createJobDetail(EventLogMsgPersist.class,
                                                schedulerManager.createJobKey(EventLogMsgPersist.class.getName()),
                                                null),
                schedulerManager.createIntervalTrigger(schedulerManager.createTriggerKey(EventLogMsgPersist.class.getName()),
                                                        Integer.valueOf(qif.getBatchConfig().getEventLogMsgPersistInterval()),
                                                        true));

        schedulerManager.add(
                schedulerManager.createJobDetail(EventLogPersist.class,
                        schedulerManager.createJobKey(EventLogPersist.class.getName()),
                        null),
                schedulerManager.createIntervalTrigger(schedulerManager.createTriggerKey(EventLogPersist.class.getName()),
                        Integer.valueOf(qif.getBatchConfig().getEventLogPersistInterval()),
                        true));

        // activity log
        schedulerManager.add(
                schedulerManager.createJobDetail(ActivityLogPersist.class,
                        schedulerManager.createJobKey(ActivityLogPersist.class.getName()),
                        null),
                schedulerManager.createIntervalTrigger(schedulerManager.createTriggerKey(ActivityLogPersist.class.getName()),
                        Integer.valueOf(qif.getBatchConfig().getActivityLogPersistInterval()),
                        true));

        schedulerManager.add(
                schedulerManager.createJobDetail(ActivityLogDataPersist.class,
                        schedulerManager.createJobKey(ActivityLogDataPersist.class.getName()),
                        null),
                schedulerManager.createIntervalTrigger(schedulerManager.createTriggerKey(ActivityLogDataPersist.class.getName()),
                        Integer.valueOf(qif.getBatchConfig().getActivityLogDataPersistInterval()),
                        true));

        schedulerManager.add(
                schedulerManager.createJobDetail(ActivityLogInputMsgPersist.class,
                        schedulerManager.createJobKey(ActivityLogInputMsgPersist.class.getName()),
                        null),
                schedulerManager.createIntervalTrigger(schedulerManager.createTriggerKey(ActivityLogInputMsgPersist.class.getName()),
                        Integer.valueOf(qif.getBatchConfig().getActivityLogInputMsgPersistInterval()),
                        true));

        schedulerManager.add(
                schedulerManager.createJobDetail(ActivityLogOutputMsgPersist.class,
                        schedulerManager.createJobKey(ActivityLogOutputMsgPersist.class.getName()),
                        null),
                schedulerManager.createIntervalTrigger(schedulerManager.createTriggerKey(ActivityLogOutputMsgPersist.class.getName()),
                        Integer.valueOf(qif.getBatchConfig().getActivityLogOutputMsgPersistInterval()),
                        true));

        // Counter update
        schedulerManager.add(
                schedulerManager.createJobDetail(CounterUpdate.class,
                        schedulerManager.createJobKey(CounterUpdate.class.getName()),
                        null),
                schedulerManager.createIntervalTrigger(schedulerManager.createTriggerKey(CounterUpdate.class.getName()),
                        Integer.valueOf(qif.getBatchConfig().getCounterUpdateInterval()),
                        true));

        if (!schedulerManager.isStarted()) {
            schedulerManager.start();
        }

    }
}
