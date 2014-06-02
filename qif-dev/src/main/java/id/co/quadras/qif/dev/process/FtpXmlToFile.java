package id.co.quadras.qif.dev.process;

import id.co.quadras.qif.dev.guice.GuiceFactory;
import id.co.quadras.qif.dev.task.WriteToFile;
import id.co.quadras.qif.model.vo.QifActivityResult;

import java.util.List;

/**
 * @author irwin Timestamp : 25/05/2014 0:40
 */
public class FtpXmlToFile extends FtpEventProcess {

    @Override
    protected QifActivityResult implementProcess(Object processInput) {
        List<String> bosList = (List<String>) processInput;
        executeTask(GuiceFactory.getInjector(), WriteToFile.class, bosList);
        return new QifActivityResult(SUCCESS, null, null);
    }

    @Override
    public String activityName() {
        return getClass().getSimpleName();
    }
}
