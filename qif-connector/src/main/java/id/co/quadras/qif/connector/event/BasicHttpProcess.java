package id.co.quadras.qif.connector.event;

import id.co.quadras.qif.core.QifActivityMessage;
import id.co.quadras.qif.core.QifProcess;
import id.co.quadras.qif.core.model.entity.QifEvent;
import id.co.quadras.qif.core.model.vo.HttpRequestMessage;
import id.co.quadras.qif.core.model.vo.message.QifMessageType;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author irwin Timestamp : 11/07/2014 13:37
 */
public abstract class BasicHttpProcess extends QifProcess {

    @Override
    protected QifActivityMessage receiveEvent(QifEvent qifEvent, Object inputMessage, QifMessageType messageType) {
        HttpRequestMessage requestMessage = (HttpRequestMessage) inputMessage;
        Map<String, Object> messageHeader = new WeakHashMap<String, Object>();
        Map<String, String> httpHeader = requestMessage.getHttpHeader();
        if (httpHeader != null) {
            for (Map.Entry<String, String> entry : httpHeader.entrySet()) {
                messageHeader.put(entry.getKey(), entry.getValue());
            }
        }
        QifActivityMessage qifActivityMessage = new QifActivityMessage(requestMessage, QifMessageType.OBJECT);
        qifActivityMessage.setMessageHeader(messageHeader);
        return qifActivityMessage;
    }
}
