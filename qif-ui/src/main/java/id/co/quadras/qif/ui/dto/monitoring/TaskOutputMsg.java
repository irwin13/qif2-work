package id.co.quadras.qif.ui.dto.monitoring;

import com.irwin13.winwork.basic.model.entity.WinWorkBasicEntity;
import com.irwin13.winwork.basic.utilities.PojoUtil;

/**
 * @author irwin Timestamp : 07/07/2014 15:42
 */
public class TaskOutputMsg extends WinWorkBasicEntity {

    private String activityLogId;
    private String outputMessageContent;
    private String msgType;

    @Override
    public String toString() {
        return PojoUtil.beanToString(this, true);
    }

    public String getActivityLogId() {
        return activityLogId;
    }

    public void setActivityLogId(String activityLogId) {
        this.activityLogId = activityLogId;
    }

    public String getOutputMessageContent() {
        return outputMessageContent;
    }

    public void setOutputMessageContent(String outputMessageContent) {
        this.outputMessageContent = outputMessageContent;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }
}
