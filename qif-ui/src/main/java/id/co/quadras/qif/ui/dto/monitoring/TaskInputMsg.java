package id.co.quadras.qif.ui.dto.monitoring;

import com.irwin13.winwork.basic.model.entity.WinWorkBasicEntity;
import com.irwin13.winwork.basic.utilities.PojoUtil;

/**
 * @author irwin Timestamp : 07/07/2014 15:42
 */
public class TaskInputMsg extends WinWorkBasicEntity {

    private String activityLogId;
    private String inputMessageContent;
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

    public String getInputMessageContent() {
        return inputMessageContent;
    }

    public void setInputMessageContent(String inputMessageContent) {
        this.inputMessageContent = inputMessageContent;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }
}
