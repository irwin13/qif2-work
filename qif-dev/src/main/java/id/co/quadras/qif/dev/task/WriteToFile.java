package id.co.quadras.qif.dev.task;

import id.co.quadras.qif.connector.adapter.FileAdapter;
import id.co.quadras.qif.core.QifTaskMessage;
import id.co.quadras.qif.core.model.entity.QifAdapter;
import id.co.quadras.qif.core.model.vo.QifActivityResult;
import id.co.quadras.qif.engine.AbstractTask;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author irwin Timestamp : 25/05/2014 0:39
 */
public class WriteToFile extends AbstractTask {

    @Override
    protected QifActivityResult implementTask(QifTaskMessage qifTaskMessage) {
        List<Map<String, String>> fileContentList = (List<Map<String, String>>) qifTaskMessage.getMessage();
        QifAdapter qifAdapter = getAdapter("IrwinFile");
        FileAdapter fileAdapter = new FileAdapter(qifAdapter);

        try {
            for (Map<String, String> fileMap : fileContentList) {
                fileAdapter.writeCharacter(fileMap.get("fileName") + ".xml", fileMap.get("fileContent"));
            }
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return new QifActivityResult(SUCCESS, null, null);
    }

}
