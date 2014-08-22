package id.co.quadras.qif.engine.process;

import id.co.quadras.qif.engine.core.QifProcess;
import id.co.quadras.qif.model.entity.QifEvent;
import id.co.quadras.qif.model.vo.QifActivityResult;
import id.co.quadras.qif.model.vo.message.QifMessageType;

/**
 * @author irwin Timestamp : 19/08/2014 19:05
 */
public abstract class DaemonProcess extends QifProcess {

    @Override
    protected QifActivityResult handleEvent(QifEvent qifEvent, Object eventMessage, QifMessageType messageType) throws Exception {
        return new QifActivityResult(SUCCESS, SUCCESS, QifMessageType.STRING);
    }
}
