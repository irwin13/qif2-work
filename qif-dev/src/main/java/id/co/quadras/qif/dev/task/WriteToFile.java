package id.co.quadras.qif.dev.task;

import id.co.quadras.qif.connector.adapter.FileAdapter;
import id.co.quadras.qif.core.QifActivityMessage;
import id.co.quadras.qif.core.model.entity.QifAdapter;
import id.co.quadras.qif.core.model.vo.QifActivityResult;
import id.co.quadras.qif.core.model.vo.message.FileMessage;
import id.co.quadras.qif.core.model.vo.message.QifMessageType;
import id.co.quadras.qif.engine.task.AbstractTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author irwin Timestamp : 25/05/2014 0:39
 */
public class WriteToFile extends AbstractTask {

    @Override
    protected QifActivityResult implementTask(QifActivityMessage qifActivityMessage) {
        QifAdapter qifAdapter = getAdapter("IrwinFile");
        FileAdapter fileAdapter = new FileAdapter(qifAdapter);

        FileMessage fileMessage = (FileMessage) qifActivityMessage.getMessageContent();

        String fileName = fileMessage.getFileName();
        String content = fileMessage.getFileContent();
        try {
            fileAdapter.writeCharacter(fileName + ".xml", content);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        QifActivityResult result = new QifActivityResult(SUCCESS, SUCCESS, QifMessageType.STRING);
        Map<String, Object> messageHeader = new HashMap<String, Object>();
        messageHeader.put("fileName", fileMessage.getFileName());

        result.setActivityData(messageHeader);
        return result;
    }

}
