package id.co.quadras.qif.dev.process;

import id.co.quadras.qif.connector.event.BasicFileProcess;
import id.co.quadras.qif.core.model.vo.QifActivityResult;
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
    protected QifActivityResult implementProcess(Object processInput) {
        List<Map<String, String>> fileList = (List<Map<String, String>>) processInput;

        if (!fileList.isEmpty()) {
            Map<String, String> fileMap = fileList.get(0);
            QifActivityResult xmlResult = executeTask(EngineFactory.getInjector(), JsonToXml.class,
                    fileMap.get("fileContent"));
            executeTask(EngineFactory.getInjector(), PutToFtp.class, xmlResult.getResult());
        }

        return new QifActivityResult(SUCCESS, null, null);
    }
}
