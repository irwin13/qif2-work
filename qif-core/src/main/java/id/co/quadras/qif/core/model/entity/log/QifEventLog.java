package id.co.quadras.qif.core.model.entity.log;

import com.irwin13.winwork.basic.model.entity.WinWorkBasicEntity;
import com.irwin13.winwork.basic.utilities.PojoUtil;
import id.co.quadras.qif.core.model.entity.QifEvent;

/**
 * @author irwin Timestamp : 24/04/2014 19:29
 */
public class QifEventLog extends WinWorkBasicEntity {

    private String eventId;
    private String referenceKey;
    private String nodeName;

    private QifEvent qifEvent;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getReferenceKey() {
        return referenceKey;
    }

    public void setReferenceKey(String referenceKey) {
        this.referenceKey = referenceKey;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public QifEvent getQifEvent() {
        return qifEvent;
    }

    public void setQifEvent(QifEvent qifEvent) {
        this.qifEvent = qifEvent;
    }

    @Override
    public String toString() {
        return PojoUtil.beanToString(this, true);
    }

}
