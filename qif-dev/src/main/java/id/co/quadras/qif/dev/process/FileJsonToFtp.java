package id.co.quadras.qif.dev.process;

import id.co.quadras.qif.dev.task.JsonToXml;
import id.co.quadras.qif.dev.task.PutToFtp;
import id.co.quadras.qif.engine.core.QifActivityMessage;
import id.co.quadras.qif.engine.core.QifUtil;
import id.co.quadras.qif.engine.process.FileProcess;
import id.co.quadras.qif.model.entity.QifEvent;
import id.co.quadras.qif.model.vo.QifActivityResult;
import id.co.quadras.qif.model.vo.message.FileMessage;
import id.co.quadras.qif.model.vo.message.QifMessageType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author irwin Timestamp : 25/05/2014 0:40
 */
public class FileJsonToFtp extends FileProcess {

    @Override
    protected QifActivityResult implementProcess(QifEvent qifEvent, QifActivityMessage qifActivityMessage) {
        QifActivityResult qifActivityResult;
        try {
            FileMessage fileMessage = (FileMessage) qifActivityMessage.getMessageContent();

            QifActivityResult xmlResult = executeTask(JsonToXml.class,
                    new QifActivityMessage(fileMessage.getFileContent(), QifMessageType.STRING));

            executeTask(PutToFtp.class,
                    new QifActivityMessage(xmlResult.getResult(), xmlResult.getMessageType()));

            qifActivityResult = new QifActivityResult(SUCCESS, SUCCESS, QifMessageType.STRING);

            Map<String, Object> activityData = new HashMap<String, Object>();
            activityData.put("fileName", fileMessage.getFileName());
            activityData.put("fileTimestamp", fileMessage.getFileTimestamp());

            qifActivityResult.setActivityData(activityData);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
            qifActivityResult = new QifActivityResult(ERROR, QifUtil.convertThrowableToJson(e), QifMessageType.STRING);
        }

        return qifActivityResult;
    }
}
