package id.co.quadras.qif.dev.process;

import id.co.quadras.qif.QifTaskMessage;
import id.co.quadras.qif.dev.guice.GuiceFactory;
import id.co.quadras.qif.dev.task.JsonToXml;
import id.co.quadras.qif.dev.task.PutToFtp;
import id.co.quadras.qif.helper.TaskRepository;
import id.co.quadras.qif.model.vo.QifActivityResult;

/**
 * @author irwin Timestamp : 25/05/2014 0:40
 */
public class FileJsonToFtp extends FileProcess {

    @Override
    protected QifActivityResult implementProcess(Object processInput) {
        JsonToXml jsonToXml = TaskRepository.getTask(GuiceFactory.getInjector(), JsonToXml.class);
        Object resultXml = jsonToXml.executeTask(new QifTaskMessage(this, processInput));

        PutToFtp putToFtp = TaskRepository.getTask(GuiceFactory.getInjector(), PutToFtp.class);
        putToFtp.executeTask(new QifTaskMessage(this, resultXml));

        return new QifActivityResult(SUCCESS, null, null);
    }


    @Override
    public String activityName() {
        return getClass().getSimpleName();
    }
}
