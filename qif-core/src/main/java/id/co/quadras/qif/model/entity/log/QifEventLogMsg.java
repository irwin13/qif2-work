package id.co.quadras.qif.model.entity.log;

import com.irwin13.winwork.basic.model.entity.WinWorkBasicEntity;
import com.irwin13.winwork.basic.utilities.PojoUtil;

/**
 * @author irwin Timestamp : 24/04/2014 19:30
 */
public class QifEventLogMsg extends WinWorkBasicEntity {

    private String eventLogId;
    private String messageContent;

    private QifEventLog qifEventLog;

    public String getEventLogId() {
        return eventLogId;
    }

    public void setEventLogId(String eventLogId) {
        this.eventLogId = eventLogId;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public QifEventLog getQifEventLog() {
        return qifEventLog;
    }

    public void setQifEventLog(QifEventLog qifEventLog) {
        this.qifEventLog = qifEventLog;
    }

    @Override
    public String toString() {
        return PojoUtil.beanToString(this, true);
    }

}
