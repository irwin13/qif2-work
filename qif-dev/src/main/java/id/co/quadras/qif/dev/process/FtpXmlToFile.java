package id.co.quadras.qif.dev.process;

import id.co.quadras.qif.connector.event.BasicFtpStringContentProcess;
import id.co.quadras.qif.core.QifActivityMessage;
import id.co.quadras.qif.core.QifUtil;
import id.co.quadras.qif.core.model.vo.QifActivityResult;
import id.co.quadras.qif.core.model.vo.message.QifMessageType;
import id.co.quadras.qif.dev.task.WriteToFile;
import id.co.quadras.qif.engine.guice.EngineFactory;

/**
 * @author irwin Timestamp : 25/05/2014 0:40
 */
public class FtpXmlToFile extends BasicFtpStringContentProcess {

    @Override
    protected QifActivityResult implementProcess(QifActivityMessage qifActivityMessage) {
        QifActivityResult qifActivityResult;
        try {
            executeTask(EngineFactory.getInjector(), WriteToFile.class, qifActivityMessage);
            qifActivityResult = new QifActivityResult(SUCCESS, SUCCESS, QifMessageType.STRING);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
            qifActivityResult = new QifActivityResult(ERROR, QifUtil.convertThrowableToJson(e), QifMessageType.STRING);
        }
        return qifActivityResult;
    }
}
