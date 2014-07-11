package id.co.quadras.qif.dev.process;

import id.co.quadras.qif.connector.event.BasicHttpProcess;
import id.co.quadras.qif.core.QifActivityMessage;
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

        try {
            QifActivityResult xmlResult = executeTask(EngineFactory.getInjector(), JsonToXml.class, qifActivityMessage);

            QifActivityMessage xmlMessage = new QifActivityMessage(((String) xmlResult.getResult()).getBytes(),
                    QifMessageType.TEXT, null);
            executeTask(EngineFactory.getInjector(), PutToFtp.class, xmlMessage);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        }

        return new QifActivityResult(SUCCESS, SUCCESS, null);
    }
}
