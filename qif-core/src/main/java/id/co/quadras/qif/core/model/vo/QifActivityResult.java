package id.co.quadras.qif.core.model.vo;

import com.irwin13.winwork.basic.utilities.PojoUtil;
import id.co.quadras.qif.core.model.vo.message.QifMessageType;

import java.util.Map;

/**
 * @author irwin Timestamp : 24/04/2014 20:31
 */
public class QifActivityResult {

    private final String status;
    private final Object result;
    private final QifMessageType messageType;
    private Map<String, Object> additionalData;


    public QifActivityResult(String status, Object result, QifMessageType messageType) {
        if (result != null) {
            if (QifMessageType.STRING.equals(messageType)) {
                if (!result.getClass().equals(String.class)) {
                    throw new IllegalArgumentException("If the messageType is STRING, then the result type must be String");
                }
            } else if (QifMessageType.BINARY.equals(messageType)) {
                if (!result.getClass().equals(byte[].class)) {
                    throw new IllegalArgumentException("If the messageType is BINARY, then the result type must be byte[]");
                }
            }
        }
        this.status = status;
        this.result = result;
        this.messageType = messageType;
    }

    public String getStatus() {
        return status;
    }

    public Object getResult() {
        return result;
    }

    public QifMessageType getMessageType() {
        return messageType;
    }

    public void setAdditionalData(Map<String, Object> additionalData) {
        this.additionalData = additionalData;
    }

    public Map<String, Object> getAdditionalData() {
        return additionalData;
    }

    @Override
    public String toString() {
        return PojoUtil.beanToString(this, false);
    }
}
