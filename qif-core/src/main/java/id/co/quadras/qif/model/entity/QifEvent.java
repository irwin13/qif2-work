package id.co.quadras.qif.model.entity;

import com.irwin13.winwork.basic.model.entity.WinWorkBasicEntity;
import com.irwin13.winwork.basic.utilities.PojoUtil;

import java.util.List;

/**
 * @author irwin Timestamp : 07/04/2014 11:53
 */
public class QifEvent extends WinWorkBasicEntity {

    private String name;
    private String eventType;
    private String eventInterface;
    private String qifProcess;
    private String description;
    private Boolean auditTrailEnabled;
    private Boolean keepMessageContent;
    private Boolean activeAcceptMessage;

    private List<QifEventProperty> qifEventPropertyList;

    private transient Object eventMessage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAuditTrailEnabled() {
        return auditTrailEnabled;
    }

    public void setAuditTrailEnabled(Boolean auditTrailEnabled) {
        this.auditTrailEnabled = auditTrailEnabled;
    }

    public Boolean getKeepMessageContent() {
        return keepMessageContent;
    }

    public void setKeepMessageContent(Boolean keepMessageContent) {
        this.keepMessageContent = keepMessageContent;
    }

    public Boolean getActiveAcceptMessage() {
        return activeAcceptMessage;
    }

    public void setActiveAcceptMessage(Boolean activeAcceptMessage) {
        this.activeAcceptMessage = activeAcceptMessage;
    }

    public List<QifEventProperty> getQifEventPropertyList() {
        return qifEventPropertyList;
    }

    public void setQifEventPropertyList(List<QifEventProperty> qifEventPropertyList) {
        this.qifEventPropertyList = qifEventPropertyList;
    }

    public Object getEventMessage() {
        return eventMessage;
    }

    public void setEventMessage(Object eventMessage) {
        this.eventMessage = eventMessage;
    }

    @Override
    public String toString() {
        return PojoUtil.beanToString(this, true);
    }

}
