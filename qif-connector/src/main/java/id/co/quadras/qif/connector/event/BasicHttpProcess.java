package id.co.quadras.qif.connector.event;

import com.google.inject.Inject;
import id.co.quadras.qif.core.QifActivityMessage;
import id.co.quadras.qif.core.QifProcess;
import id.co.quadras.qif.core.helper.JsonParser;
import id.co.quadras.qif.core.model.entity.QifEvent;
import id.co.quadras.qif.core.model.vo.HttpRequestMessage;
import id.co.quadras.qif.core.model.vo.message.QifMessageType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author irwin Timestamp : 11/07/2014 13:37
 */
public abstract class BasicHttpProcess extends QifProcess {

    public static final String HTTP_REQUEST = "httpRequest";

    @Inject
    protected JsonParser jsonParser;

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
        try {
            messageHeader.put(HTTP_REQUEST, jsonParser.parseToString(false, requestMessage));
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return new QifActivityMessage(requestMessage.getHttpBody().getBytes(),
                QifMessageType.TEXT, messageHeader);
    }
}
