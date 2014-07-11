package id.co.quadras.qif.core;

import com.irwin13.winwork.basic.utilities.PojoUtil;
import id.co.quadras.qif.core.model.vo.message.QifMessageType;

import java.util.Map;

/**
 * @author irwin Timestamp : 05/05/2014 20:04
 */
public class QifActivityMessage {

    private final byte[] content;
    private final QifMessageType messageType;
    private final Map<String, Object> messageHeader;

    public QifActivityMessage(byte[] content, QifMessageType messageType, Map<String, Object> messageHeader) {
        this.content = content;
        this.messageType = messageType;
        this.messageHeader = messageHeader;
    }

    public byte[] getContent() {
        return content;
    }

    public QifMessageType getMessageType() {
        return messageType;
    }

    public Map<String, Object> getMessageHeader() {
        return messageHeader;
    }

    @Override
    public String toString() {
        return PojoUtil.beanToString(this, false);
    }

}
