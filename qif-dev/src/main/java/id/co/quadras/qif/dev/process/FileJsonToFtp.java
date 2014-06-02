package id.co.quadras.qif.dev.process;

import id.co.quadras.qif.dev.guice.GuiceFactory;
import id.co.quadras.qif.dev.task.JsonToXml;
import id.co.quadras.qif.dev.task.PutToFtp;
import id.co.quadras.qif.model.vo.QifActivityResult;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author irwin Timestamp : 25/05/2014 0:40
 */
public class FileJsonToFtp extends FileEventProcess {

    @Override
    protected QifActivityResult implementProcess(Object processInput) {
        List<String> stringList = (List<String>) processInput;

        if (!stringList.isEmpty()) {
            QifActivityResult xmlResult = executeTask(GuiceFactory.getInjector(), JsonToXml.class, stringList.get(0));
            executeTask(GuiceFactory.getInjector(), PutToFtp.class, xmlResult.getResult());
        }

        return new QifActivityResult(SUCCESS, null, null);
    }


    @Override
    public String activityName() {
        return getClass().getSimpleName();
    }
}
