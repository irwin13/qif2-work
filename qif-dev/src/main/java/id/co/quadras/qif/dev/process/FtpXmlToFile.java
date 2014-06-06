package id.co.quadras.qif.dev.process;

import id.co.quadras.qif.dev.task.WriteToFile;
import id.co.quadras.qif.connector.event.FtpEvent;
import id.co.quadras.qif.core.model.vo.QifActivityResult;
import id.co.quadras.qif.engine.guice.GuiceFactory;

import java.util.List;

/**
 * @author irwin Timestamp : 25/05/2014 0:40
 */
public class FtpXmlToFile extends FtpEvent {

    @Override
    protected QifActivityResult implementProcess(Object processInput) {
        List<String> bosList = (List<String>) processInput;
        executeTask(GuiceFactory.getInjector(), WriteToFile.class, bosList);
        return new QifActivityResult(SUCCESS, null, null);
    }

    @Override
    public String activityName() {
        return getClass().getCanonicalName();
    }
}
