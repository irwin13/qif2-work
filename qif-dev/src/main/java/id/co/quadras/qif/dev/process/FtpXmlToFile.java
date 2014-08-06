package id.co.quadras.qif.dev.process;

import id.co.quadras.qif.core.QifActivityMessage;
import id.co.quadras.qif.core.QifUtil;
import id.co.quadras.qif.core.model.vo.QifActivityResult;
import id.co.quadras.qif.core.model.vo.message.FileMessage;
import id.co.quadras.qif.core.model.vo.message.QifMessageType;
import id.co.quadras.qif.dev.task.WriteToFile;
import id.co.quadras.qif.engine.guice.EngineFactory;
import id.co.quadras.qif.engine.process.FtpProcess;

import java.util.HashMap;
import java.util.Map;

/**
 * @author irwin Timestamp : 25/05/2014 0:40
 */
public class FtpXmlToFile extends FtpProcess {

    @Override
    protected QifActivityResult implementProcess(QifActivityMessage qifActivityMessage) {
        QifActivityResult qifActivityResult;
        try {
            FileMessage fileMessage = (FileMessage) qifActivityMessage.getMessageContent();

            executeTask(EngineFactory.getInjector(), WriteToFile.class, qifActivityMessage);

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
