package id.co.quadras.qif.ui.dto.monitoring;

import com.irwin13.winwork.basic.model.entity.WinWorkBasicEntity;
import com.irwin13.winwork.basic.utilities.PojoUtil;

/**
 * @author irwin Timestamp : 07/07/2014 15:27
 */
public class ProcessInstance extends WinWorkBasicEntity {

    private String createBySimple;

    private String nodeName;
    private String activityType;
    private String activityStatus;
    private long startTime;
    private long executionTime;
    private String eventLogId;
    private String parentActivityId;
    private String eventName;
    private String parentEventName;

    @Override
    public String toString() {
        return PojoUtil.beanToString(this, true);
    }

    public String getCreateBySimple() {
        return getCreateBy().substring(getCreateBy().lastIndexOf("."), getCreateBy().length());
    }

    public void setCreateBySimple(String createBySimple) {
        this.createBySimple = createBySimple;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    public String getEventLogId() {
        return eventLogId;
    }

    public void setEventLogId(String eventLogId) {
        this.eventLogId = eventLogId;
    }

    public String getParentActivityId() {
        return parentActivityId;
    }

    public void setParentActivityId(String parentActivityId) {
        this.parentActivityId = parentActivityId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getParentEventName() {
        return parentEventName;
    }

    public void setParentEventName(String parentEventName) {
        this.parentEventName = parentEventName;
    }
}
