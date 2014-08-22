package id.co.quadras.qif.engine.core;

import com.irwin13.winwork.basic.utilities.PojoUtil;
import id.co.quadras.qif.model.vo.message.QifMessageType;

import java.util.Map;

/**
 * @author irwin Timestamp : 18/07/2014 14:16
 */
public class QifActivityMessage {

    private final Object messageContent;
    private final QifMessageType messageType;
    private Map<String, Object> messageHeader;

    public QifActivityMessage(Object messageContent, QifMessageType messageType) {
        if (messageContent != null) {
            if (QifMessageType.STRING.equals(messageType)) {
                if (!messageContent.getClass().equals(String.class)) {
                    throw new IllegalArgumentException("If the messageType is STRING, then the messageContent type must be String");
                }
            } else if (QifMessageType.BINARY.equals(messageType)) {
                if (!messageContent.getClass().equals(byte[].class)) {
                    throw new IllegalArgumentException("If the messageType is BINARY, then the messageContent type must be byte[]");
                }
            }
        }
        this.messageContent = messageContent;
        this.messageType = messageType;
    }

    public Object getMessageContent() {
        return messageContent;
    }

    public QifMessageType getMessageType() {
        return messageType;
    }

    public Map<String, Object> getMessageHeader() {
        return messageHeader;
    }

    public void setMessageHeader(Map<String, Object> messageHeader) {
        this.messageHeader = messageHeader;
    }

    @Override
    public String toString() {
        return PojoUtil.beanToString(this, false);
    }
}
