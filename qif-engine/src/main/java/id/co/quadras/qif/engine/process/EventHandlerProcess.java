package id.co.quadras.qif.engine.process;

import id.co.quadras.qif.engine.core.QifProcess;
import id.co.quadras.qif.model.entity.QifEvent;

/**
 * @author irwin Timestamp : 19/08/2014 19:04
 */
public abstract class EventHandlerProcess extends QifProcess {

    @Override
    public Runnable createDaemon(QifEvent qifEvent) {
        return null;
    }

}
