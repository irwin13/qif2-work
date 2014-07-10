package id.co.quadras.qif.dev.process;

import id.co.quadras.qif.core.QifProcess;
import id.co.quadras.qif.core.model.entity.QifEvent;
import id.co.quadras.qif.core.model.vo.HttpRequestMessage;
import id.co.quadras.qif.core.model.vo.QifActivityResult;
import id.co.quadras.qif.dev.task.JsonToXml;
import id.co.quadras.qif.dev.task.PutToFtp;
import id.co.quadras.qif.engine.guice.EngineFactory;

/**
 * @author irwin Timestamp : 13/06/2014 17:08
 */
public class HttpToFtp extends QifProcess {

    @Override
    protected Object receiveEvent(QifEvent qifEvent, Object inputMessage) {
        HttpRequestMessage requestMessage = (HttpRequestMessage) inputMessage;
        return requestMessage.getHttpBody();
    }

    @Override
    protected QifActivityResult implementProcess(Object processInput) {
        QifActivityResult xmlResult = executeTask(EngineFactory.getInjector(), JsonToXml.class, processInput);
        executeTask(EngineFactory.getInjector(), PutToFtp.class, xmlResult.getResult());

        return new QifActivityResult(SUCCESS, SUCCESS, null);
    }
}