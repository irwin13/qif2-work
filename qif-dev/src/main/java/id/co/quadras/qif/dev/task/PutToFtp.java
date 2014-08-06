package id.co.quadras.qif.dev.task;

import id.co.quadras.qif.connector.adapter.FtpAdapter;
import id.co.quadras.qif.core.QifActivityMessage;
import id.co.quadras.qif.core.model.entity.QifAdapter;
import id.co.quadras.qif.core.model.vo.QifActivityResult;
import id.co.quadras.qif.core.model.vo.message.QifMessageType;
import id.co.quadras.qif.engine.task.AbstractTask;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author irwin Timestamp : 25/05/2014 0:39
 */
public class PutToFtp extends AbstractTask {

    @Override
    protected QifActivityResult implementTask(QifActivityMessage qifActivityMessage) {
        QifAdapter qifAdapter = getAdapter("IrwinFtp");
        logger.debug("IrwinFtp adapter = {}", qifAdapter);
        String fileName = "book.xml";

        Map<String, Object> mapData = new HashMap<String, Object>();
        mapData.put("fileName", fileName);

        FtpAdapter ftpAdapter = new FtpAdapter(qifAdapter);
        String content = (String) qifActivityMessage.getMessageContent();
        try {
            ftpAdapter.connect();
            ftpAdapter.storeFile(fileName,
                    new ByteArrayInputStream(content.getBytes()));
            ftpAdapter.disconnect();
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
        }

        QifActivityResult result = new QifActivityResult(SUCCESS, SUCCESS, QifMessageType.STRING);
        result.setActivityData(mapData);
        return result;
    }

}
