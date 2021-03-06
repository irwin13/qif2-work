package id.co.quadras.qif.engine.process;

import id.co.quadras.qif.engine.connector.event.HttpEventHandler;
import id.co.quadras.qif.model.entity.QifEvent;
import id.co.quadras.qif.model.vo.QifActivityResult;
import id.co.quadras.qif.model.vo.message.QifMessageType;

/**
 * @author irwin Timestamp : 06/08/2014 14:13
 */
public abstract class HttpProcess extends EventHandlerProcess {

    @Override
    protected QifActivityResult handleEvent(QifEvent qifEvent, Object eventMessage, QifMessageType messageType)
            throws Exception {
        HttpEventHandler httpEventHandler = new HttpEventHandler(qifEvent, eventMessage);
        return executeProcess(qifEvent, httpEventHandler.convertHttpRequest(eventMessage));
    }
}
