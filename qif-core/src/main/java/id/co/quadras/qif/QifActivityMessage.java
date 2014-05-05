package id.co.quadras.qif;

import com.irwin13.winwork.basic.utilities.PojoUtil;
import id.co.quadras.qif.model.entity.log.QifActivityLog;
import id.co.quadras.qif.model.entity.log.QifEventLog;

/**
 * @author irwin Timestamp : 05/05/2014 20:04
 */
public class QifActivityMessage {

    private final QifEventLog qifEventLog;
    private final Object message;
    private final QifActivityLog parentActivityLog;

    public QifActivityMessage(QifEventLog qifEventLog, Object message, QifActivityLog parentActivityLog) {
        this.qifEventLog = qifEventLog;
        this.message = message;
        this.parentActivityLog = parentActivityLog;
    }

    public QifEventLog getQifEventLog() {
        return qifEventLog;
    }

    public Object getMessage() {
        return message;
    }

    public QifActivityLog getParentActivityLog() {
        return parentActivityLog;
    }

    @Override
    public String toString() {
        return PojoUtil.beanToString(this, false);
    }

}
