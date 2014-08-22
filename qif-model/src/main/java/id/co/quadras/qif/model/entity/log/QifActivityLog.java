package id.co.quadras.qif.model.entity.log;

import com.irwin13.winwork.basic.model.entity.WinWorkBasicEntity;
import com.irwin13.winwork.basic.utilities.PojoUtil;

import java.util.List;

/**
 * @author irwin Timestamp : 24/04/2014 19:30
 */
public class QifActivityLog extends WinWorkBasicEntity {

    private String nodeName;
    private String activityType;
    private String activityStatus;
    private long startTime;
    private long executionTime;
    private String eventLogId;
    private String parentActivityId;

    private QifEventLog qifEventLog;
    private QifActivityLog parentActivity;

    private List<QifActivityLogData> qifActivityLogDataList;

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

    public List<QifActivityLogData> getQifActivityLogDataList() {
        return qifActivityLogDataList;
    }

    public void setQifActivityLogDataList(List<QifActivityLogData> qifActivityLogDataList) {
        this.qifActivityLogDataList = qifActivityLogDataList;
    }

    public QifEventLog getQifEventLog() {
        return qifEventLog;
    }

    public void setQifEventLog(QifEventLog qifEventLog) {
        this.qifEventLog = qifEventLog;
    }

    public QifActivityLog getParentActivity() {
        return parentActivity;
    }

    public void setParentActivity(QifActivityLog parentActivity) {
        this.parentActivity = parentActivity;
    }

    @Override
    public String toString() {
        return PojoUtil.beanToString(this, true);
    }

}
