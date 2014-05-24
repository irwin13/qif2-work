package id.co.quadras.qif.dev.task;

import id.co.quadras.qif.QifActivityMessage;
import id.co.quadras.qif.QifTask;
import id.co.quadras.qif.model.vo.QifActivityResult;

/**
 * @author irwin Timestamp : 25/05/2014 0:40
 */
public class XmlToJson extends QifTask {

    @Override
    protected QifActivityResult implementTask(QifActivityMessage qifActivityMessage) {
        return null;
    }

    @Override
    public String activityName() {
        return getClass().getSimpleName();
    }
}
