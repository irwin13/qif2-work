package id.co.quadras.qif.dev.process;

import id.co.quadras.qif.connector.event.BasicFtpProcess;
import id.co.quadras.qif.core.QifActivityMessage;
import id.co.quadras.qif.core.model.vo.QifActivityResult;
import id.co.quadras.qif.dev.task.WriteToFile;
import id.co.quadras.qif.engine.guice.EngineFactory;

import java.util.List;
import java.util.Map;

/**
 * @author irwin Timestamp : 25/05/2014 0:40
 */
public class FtpXmlToFile extends BasicFtpProcess {

    @Override
    protected QifActivityResult implementProcess(QifActivityMessage qifActivityMessage) {
        try {
            executeTask(EngineFactory.getInjector(), WriteToFile.class, qifActivityMessage);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return new QifActivityResult(SUCCESS, null, null);
    }
}
