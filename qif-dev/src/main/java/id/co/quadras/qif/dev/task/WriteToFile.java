package id.co.quadras.qif.dev.task;

import id.co.quadras.qif.connector.adapter.FileAdapter;
import id.co.quadras.qif.core.QifTaskMessage;
import id.co.quadras.qif.core.model.entity.QifAdapter;
import id.co.quadras.qif.core.model.vo.QifActivityResult;

import java.io.IOException;
import java.util.List;

/**
 * @author irwin Timestamp : 25/05/2014 0:39
 */
public class WriteToFile extends AbstractTask {

    @Override
    protected QifActivityResult implementTask(QifTaskMessage qifTaskMessage) {
        List<String> fileContentList = (List<String>) qifTaskMessage.getMessage();
        QifAdapter qifAdapter = getAdapter("IrwinFile");
        FileAdapter fileAdapter = new FileAdapter(qifAdapter);
        int index = 1;

        try {
            for (String fileContent : fileContentList) {
                fileAdapter.writeCharacter(index + ".xml", fileContent);
                index++;
            }
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return new QifActivityResult(SUCCESS, null, null);
    }

    @Override
    public String activityName() {
        return getClass().getCanonicalName();
    }
}
