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
    private final Map<String, Object> additionalData;


    public QifActivityResult(String status, Object result, QifMessageType messageType,
                             Map<String, Object> additionalData) {
        this.status = status;
        this.result = result;
        this.messageType = messageType;
        this.additionalData = additionalData;
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

    public Map<String, Object> getAdditionalData() {
        return additionalData;
    }

    @Override
    public String toString() {
        return PojoUtil.beanToString(this, false);
    }
}
