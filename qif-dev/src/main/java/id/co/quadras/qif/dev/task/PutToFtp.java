package id.co.quadras.qif.dev.task;

import id.co.quadras.qif.QifTaskMessage;
import id.co.quadras.qif.adapter.FtpAdapter;
import id.co.quadras.qif.model.entity.QifAdapter;
import id.co.quadras.qif.model.vo.QifActivityResult;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * @author irwin Timestamp : 25/05/2014 0:39
 */
public class PutToFtp extends AbstractTask {

    @Override
    protected QifActivityResult implementTask(QifTaskMessage qifTaskMessage) {
        QifAdapter qifAdapter = getAdapter("IrwinFtp");
        FtpAdapter ftpAdapter = new FtpAdapter(qifAdapter);
        try {
            ftpAdapter.connect();
            ftpAdapter.storeFile("book.xml", new ByteArrayInputStream(((String) qifTaskMessage.getMessage()).getBytes()));
            ftpAdapter.disconnect();
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return new QifActivityResult(SUCCESS, null, null);
    }

    @Override
    public String activityName() {
        return getClass().getSimpleName();
    }
}
