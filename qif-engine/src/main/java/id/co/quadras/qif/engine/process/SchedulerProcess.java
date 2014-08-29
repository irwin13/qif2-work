package id.co.quadras.qif.engine.process;

import id.co.quadras.qif.engine.core.QifActivityMessage;
import id.co.quadras.qif.model.entity.QifEvent;
import id.co.quadras.qif.model.vo.QifActivityResult;
import id.co.quadras.qif.model.vo.message.QifMessageType;

/**
 * @author irwin Timestamp : 29/08/2014 11:03
 */
public abstract class SchedulerProcess extends EventHandlerProcess {

    @Override
    protected QifActivityResult handleEvent(QifEvent qifEvent, Object eventMessage, QifMessageType messageType) throws Exception {
        executeProcess(qifEvent, new QifActivityMessage(eventMessage, messageType));
        return new QifActivityResult(SUCCESS, SUCCESS, QifMessageType.STRING);
    }
}
