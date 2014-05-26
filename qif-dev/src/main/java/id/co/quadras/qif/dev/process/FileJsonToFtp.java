package id.co.quadras.qif.dev.process;

import id.co.quadras.qif.QifActivityMessage;
import id.co.quadras.qif.dev.guice.GuiceFactory;
import id.co.quadras.qif.dev.task.JsonToXml;
import id.co.quadras.qif.dev.task.PutToFtp;
import id.co.quadras.qif.model.vo.QifActivityResult;

/**
 * @author irwin Timestamp : 25/05/2014 0:40
 */
public class FileJsonToFtp extends FileProcess {

    @Override
    protected QifActivityResult implementProcess(Object processInput) {
        JsonToXml jsonToXml = GuiceFactory.getInjector().getInstance(JsonToXml.class);
        Object resultXml = jsonToXml.executeTask(new QifActivityMessage(getQifEventLog(),
                processInput,
                getProcessActivityLog()));

        PutToFtp putToFtp = GuiceFactory.getInjector().getInstance(PutToFtp.class);
        putToFtp.executeTask(new QifActivityMessage(getQifEventLog(),
                resultXml,
                getProcessActivityLog()));

        return new QifActivityResult(SUCCESS, null, null);
    }


    @Override
    public String activityName() {
        return getClass().getSimpleName();
    }
}
