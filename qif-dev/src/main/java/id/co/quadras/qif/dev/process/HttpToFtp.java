package id.co.quadras.qif.dev.process;

import id.co.quadras.qif.connector.event.BasicHttpProcess;
import id.co.quadras.qif.core.QifActivityMessage;
import id.co.quadras.qif.core.QifUtil;
import id.co.quadras.qif.core.model.vo.HttpRequestMessage;
import id.co.quadras.qif.core.model.vo.QifActivityResult;
import id.co.quadras.qif.core.model.vo.message.QifMessageType;
import id.co.quadras.qif.dev.task.JsonToXml;
import id.co.quadras.qif.dev.task.PutToFtp;
import id.co.quadras.qif.engine.guice.EngineFactory;

/**
 * @author irwin Timestamp : 13/06/2014 17:08
 */
public class HttpToFtp extends BasicHttpProcess {

    @Override
    protected QifActivityResult implementProcess(QifActivityMessage qifActivityMessage) {
        QifActivityResult qifActivityResult;

        try {
            HttpRequestMessage httpRequestMessage = (HttpRequestMessage) qifActivityMessage.getMessageContent();

            QifActivityResult xmlResult = executeTask(EngineFactory.getInjector(), JsonToXml.class,
                    new QifActivityMessage(httpRequestMessage.getHttpBody(), QifMessageType.STRING));

            executeTask(EngineFactory.getInjector(), PutToFtp.class,
                    new QifActivityMessage(xmlResult.getResult(), xmlResult.getMessageType()));

            qifActivityResult = new QifActivityResult(SUCCESS, SUCCESS, QifMessageType.STRING);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
            qifActivityResult = new QifActivityResult(ERROR, QifUtil.convertThrowableToJson(e), QifMessageType.STRING);
        }

        return qifActivityResult;
    }
}
