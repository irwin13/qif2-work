package id.co.quadras.qif.dev.process;

import id.co.quadras.qif.connector.event.BasicFileProcess;
import id.co.quadras.qif.core.QifActivityMessage;
import id.co.quadras.qif.core.model.vo.QifActivityResult;
import id.co.quadras.qif.core.model.vo.message.QifMessageType;
import id.co.quadras.qif.dev.task.JsonToXml;
import id.co.quadras.qif.dev.task.PutToFtp;
import id.co.quadras.qif.engine.guice.EngineFactory;

import java.util.List;
import java.util.Map;

/**
 * @author irwin Timestamp : 25/05/2014 0:40
 */
public class FileJsonToFtp extends BasicFileProcess {

    @Override
    protected QifActivityResult implementProcess(QifActivityMessage qifActivityMessage) {

        try {
            QifActivityResult xmlResult = executeTask(EngineFactory.getInjector(), JsonToXml.class, qifActivityMessage);
            QifActivityMessage ftpMessage = new QifActivityMessage(((String) xmlResult.getResult()).getBytes(), QifMessageType.TEXT, null);
            executeTask(EngineFactory.getInjector(), PutToFtp.class, ftpMessage);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        }


        return new QifActivityResult(SUCCESS, null, QifMessageType.TEXT, null);
    }
}
