package id.co.quadras.qif.dev.process;

import id.co.quadras.qif.QifTaskMessage;
import id.co.quadras.qif.dev.guice.GuiceFactory;
import id.co.quadras.qif.dev.task.JsonToXml;
import id.co.quadras.qif.dev.task.PutToFtp;
import id.co.quadras.qif.model.vo.QifActivityResult;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author irwin Timestamp : 25/05/2014 0:40
 */
public class FileJsonToFtp extends FileProcess {

    @Override
    protected QifActivityResult implementProcess(Object processInput) {
        File[] files = (File[]) processInput;
        String json = null;
        try {
            json = FileUtils.readFileToString(files[0]);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        QifActivityResult xmlResult = executeTask(GuiceFactory.getInjector(), JsonToXml.class,
                new QifTaskMessage(this, json));

        executeTask(GuiceFactory.getInjector(), PutToFtp.class,
                new QifTaskMessage(this, xmlResult.getResult()));

        return new QifActivityResult(SUCCESS, null, null);
    }


    @Override
    public String activityName() {
        return getClass().getSimpleName();
    }
}
