package id.co.quadras.qif.dev.process;

import id.co.quadras.qif.dev.task.JsonToXml;
import id.co.quadras.qif.dev.task.PutToFtp;
import id.co.quadras.qif.engine.core.QifActivityMessage;
import id.co.quadras.qif.engine.core.QifUtil;
import id.co.quadras.qif.engine.process.HttpProcess;
import id.co.quadras.qif.model.vo.HttpRequestMessage;
import id.co.quadras.qif.model.vo.QifActivityResult;
import id.co.quadras.qif.model.vo.message.QifMessageType;

/**
 * @author irwin Timestamp : 13/06/2014 17:08
 */
public class HttpToFtp extends HttpProcess {

    @Override
    protected QifActivityResult implementProcess(QifActivityMessage qifActivityMessage) {
        QifActivityResult qifActivityResult;

        try {
            HttpRequestMessage httpRequestMessage = (HttpRequestMessage) qifActivityMessage.getMessageContent();

            QifActivityResult xmlResult = executeTask(JsonToXml.class,
                    new QifActivityMessage(httpRequestMessage.getHttpBody(), QifMessageType.STRING));

            executeTask(PutToFtp.class,
                    new QifActivityMessage(xmlResult.getResult(), xmlResult.getMessageType()));

            qifActivityResult = new QifActivityResult(SUCCESS, SUCCESS, QifMessageType.STRING);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
            qifActivityResult = new QifActivityResult(ERROR, QifUtil.convertThrowableToJson(e), QifMessageType.STRING);
        }

        return qifActivityResult;
    }
}
