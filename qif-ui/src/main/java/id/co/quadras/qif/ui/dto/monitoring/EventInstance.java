package id.co.quadras.qif.ui.dto.monitoring;

import com.irwin13.winwork.basic.model.entity.WinWorkBasicEntity;
import com.irwin13.winwork.basic.utilities.PojoUtil;

/**
 * @author irwin Timestamp : 07/07/2014 15:27
 */
public class EventInstance extends WinWorkBasicEntity {

    private String createBySimple;

    private String eventId;
    private String referenceKey;
    private String nodeName;
    private String eventName;
    private String eventType;
    private String eventInterface;
    private String qifProcess;

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

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventInterface() {
        return eventInterface;
    }

    public void setEventInterface(String eventInterface) {
        this.eventInterface = eventInterface;
    }

    public String getQifProcess() {
        return qifProcess;
    }

    public void setQifProcess(String qifProcess) {
        this.qifProcess = qifProcess;
    }
}
