package id.co.quadras.qif.model.entity.log;

import com.irwin13.winwork.basic.model.entity.WinWorkBasicEntity;
import com.irwin13.winwork.basic.utilities.PojoUtil;

/**
 * @author irwin Timestamp : 24/04/2014 19:30
 */
public class QifActivityLogInputMsg extends WinWorkBasicEntity {

    private String activityLogId;
    private String inputMessageContent;
    private String msgType;

    private QifActivityLog qifActivityLog;

    public String getActivityLogId() {
        return activityLogId;
    }

    public void setActivityLogId(String activityLogId) {
        this.activityLogId = activityLogId;
    }

    public String getInputMessageContent() {
        return inputMessageContent;
    }

    public void setInputMessageContent(String inputMessageContent) {
        this.inputMessageContent = inputMessageContent;
    }

    public QifActivityLog getQifActivityLog() {
        return qifActivityLog;
    }

    public void setQifActivityLog(QifActivityLog qifActivityLog) {
        this.qifActivityLog = qifActivityLog;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    @Override
    public String toString() {
        return PojoUtil.beanToString(this, true);
    }

}
