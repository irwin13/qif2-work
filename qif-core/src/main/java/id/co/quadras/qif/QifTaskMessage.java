package id.co.quadras.qif;

import com.irwin13.winwork.basic.utilities.PojoUtil;

/**
 * @author irwin Timestamp : 05/05/2014 20:04
 */
public class QifTaskMessage {

    private final QifProcess qifProcess;
    private final Object message;

    public QifTaskMessage(QifProcess qifProcess, Object inputMessage) {
        this.qifProcess = qifProcess;
        this.message = inputMessage;
    }

    public QifProcess getQifProcess() {
        return qifProcess;
    }

    public Object getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return PojoUtil.beanToString(this, false);
    }

}
