package id.co.quadras.qif.dev.task;

import id.co.quadras.qif.connector.adapter.FileAdapter;
import id.co.quadras.qif.core.QifActivityMessage;
import id.co.quadras.qif.core.model.entity.QifAdapter;
import id.co.quadras.qif.core.model.vo.QifActivityResult;
import id.co.quadras.qif.core.model.vo.message.QifMessageType;
import id.co.quadras.qif.engine.AbstractTask;

import java.io.IOException;
import java.util.Map;

/**
 * @author irwin Timestamp : 25/05/2014 0:39
 */
public class WriteToFile extends AbstractTask {

    @Override
    protected QifActivityResult implementTask(QifActivityMessage qifActivityMessage) {
        QifAdapter qifAdapter = getAdapter("IrwinFile");
        FileAdapter fileAdapter = new FileAdapter(qifAdapter);

        Map<String, Object> messageHeader = qifActivityMessage.getMessageHeader();
        String fileName = (String) messageHeader.get("fileName");
        try {
            fileAdapter.writeCharacter(fileName + ".xml", new String(qifActivityMessage.getContent()));
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return new QifActivityResult(SUCCESS, null, QifMessageType.TEXT, null);
    }

}
