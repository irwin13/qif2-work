package id.co.quadras.qif.dev.task;

import id.co.quadras.qif.QifTaskMessage;
import id.co.quadras.qif.model.vo.QifActivityResult;

/**
 * @author irwin Timestamp : 25/05/2014 0:39
 */
public class SendEmail extends AbstractTask {

    @Override
    protected QifActivityResult implementTask(QifTaskMessage qifTaskMessage) {
        return null;
    }

    @Override
    public String activityName() {
        return getClass().getSimpleName();
    }
}
