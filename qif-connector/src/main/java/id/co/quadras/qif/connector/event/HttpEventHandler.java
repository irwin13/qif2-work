package id.co.quadras.qif.connector.event;

import id.co.quadras.qif.core.QifActivityMessage;
import id.co.quadras.qif.core.QifEventHandler;
import id.co.quadras.qif.core.model.entity.QifEvent;
import id.co.quadras.qif.core.model.vo.HttpRequestMessage;
import id.co.quadras.qif.core.model.vo.message.QifMessageType;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author irwin Timestamp : 11/07/2014 13:37
 */
public class HttpEventHandler extends QifEventHandler {

    public HttpEventHandler(QifEvent qifEvent, Object eventMessage) {
        super(qifEvent, eventMessage);
    }

    public QifActivityMessage convertHttpRequest(Object inputMessage) {
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
