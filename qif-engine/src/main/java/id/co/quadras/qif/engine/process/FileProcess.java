package id.co.quadras.qif.engine.process;

import id.co.quadras.qif.engine.connector.event.FileEventHandler;
import id.co.quadras.qif.engine.core.QifActivityMessage;
import id.co.quadras.qif.model.entity.QifEvent;
import id.co.quadras.qif.model.vo.QifActivityResult;
import id.co.quadras.qif.model.vo.message.FileMessage;
import id.co.quadras.qif.model.vo.message.QifMessageType;

import java.util.LinkedList;
import java.util.List;

/**
 * @author irwin Timestamp : 06/08/2014 14:14
 */
public abstract class FileProcess extends EventHandlerProcess {

    @Override
    protected QifActivityResult handleEvent(QifEvent qifEvent, Object eventMessage, QifMessageType messageType)
            throws Exception {

        List<QifActivityResult> resultList = new LinkedList<QifActivityResult>();
        FileEventHandler fileEventHandler = new FileEventHandler(qifEvent, eventMessage);
        List<FileMessage> fileMessageList = fileEventHandler.getFiles();

        boolean allSuccess = true;
        for (FileMessage fileMessage : fileMessageList) {
            QifActivityResult result = executeProcess(qifEvent, new QifActivityMessage(fileMessage, QifMessageType.OBJECT));
            if (ERROR.equals(result.getStatus())) {
                allSuccess = false;
            }
            resultList.add(result);
        }

        return new QifActivityResult((allSuccess) ? SUCCESS : ERROR, resultList, QifMessageType.OBJECT);
    }
}
