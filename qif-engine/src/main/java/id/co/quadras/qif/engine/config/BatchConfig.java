package id.co.quadras.qif.engine.config;

import com.google.common.base.Objects;

import javax.validation.constraints.NotNull;

/**
 * @author irwin Timestamp : 22/08/2014 16:05
 */
public class BatchConfig {

    @NotNull
    private int eventLogPersistInterval;
    @NotNull
    private int eventLogMsgPersistInterval;
    @NotNull
    private int activityLogPersistInterval;
    @NotNull
    private int activityLogDataPersistInterval;
    @NotNull
    private int activityLogInputMsgPersistInterval;
    @NotNull
    private int activityLogOutputMsgPersistInterval;
    @NotNull
    private int counterUpdateInterval;

    public int getEventLogPersistInterval() {
        return eventLogPersistInterval;
    }

    public void setEventLogPersistInterval(int eventLogPersistInterval) {
        this.eventLogPersistInterval = eventLogPersistInterval;
    }

    public int getEventLogMsgPersistInterval() {
        return eventLogMsgPersistInterval;
    }

    public void setEventLogMsgPersistInterval(int eventLogMsgPersistInterval) {
        this.eventLogMsgPersistInterval = eventLogMsgPersistInterval;
    }

    public int getActivityLogPersistInterval() {
        return activityLogPersistInterval;
    }

    public void setActivityLogPersistInterval(int activityLogPersistInterval) {
        this.activityLogPersistInterval = activityLogPersistInterval;
    }

    public int getActivityLogDataPersistInterval() {
        return activityLogDataPersistInterval;
    }

    public void setActivityLogDataPersistInterval(int activityLogDataPersistInterval) {
        this.activityLogDataPersistInterval = activityLogDataPersistInterval;
    }

    public int getActivityLogInputMsgPersistInterval() {
        return activityLogInputMsgPersistInterval;
    }

    public void setActivityLogInputMsgPersistInterval(int activityLogInputMsgPersistInterval) {
        this.activityLogInputMsgPersistInterval = activityLogInputMsgPersistInterval;
    }

    public int getActivityLogOutputMsgPersistInterval() {
        return activityLogOutputMsgPersistInterval;
    }

    public void setActivityLogOutputMsgPersistInterval(int activityLogOutputMsgPersistInterval) {
        this.activityLogOutputMsgPersistInterval = activityLogOutputMsgPersistInterval;
    }

    public int getCounterUpdateInterval() {
        return counterUpdateInterval;
    }

    public void setCounterUpdateInterval(int counterUpdateInterval) {
        this.counterUpdateInterval = counterUpdateInterval;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("eventLogPersistInterval", eventLogPersistInterval)
                .add("eventLogMsgPersistInterval", eventLogMsgPersistInterval)
                .add("activityLogPersistInterval", activityLogPersistInterval)
                .add("activityLogDataPersistInterval", activityLogDataPersistInterval)
                .add("activityLogInputMsgPersistInterval", activityLogInputMsgPersistInterval)
                .add("activityLogOutputMsgPersistInterval", activityLogOutputMsgPersistInterval)
                .add("counterUpdateInterval", counterUpdateInterval)
                .toString();
    }
}
