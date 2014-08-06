package id.co.quadras.qif.core.helper.queue.reader.imp;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import id.co.quadras.qif.core.helper.queue.ActivityLogInputMsgQueue;
import id.co.quadras.qif.core.helper.queue.reader.ActivityLogInputMsgQueueReader;
import id.co.quadras.qif.core.model.entity.log.QifActivityLogInputMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * @author irwin Timestamp : 05/05/2014 11:51
 */
public class ActivityLogInputMsgQueueReaderImp implements ActivityLogInputMsgQueueReader {

    private final ActivityLogInputMsgQueue queue;
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityLogInputMsgQueueReaderImp.class);

    @Inject
    public ActivityLogInputMsgQueueReaderImp(ActivityLogInputMsgQueue queue) {
        this.queue = queue;
    }

    @Override
    public List<QifActivityLogInputMsg> getLogList(int maxFetch) {
        Preconditions.checkArgument(maxFetch > 0, "maxFetch cannot be less than or equals 0");
        List<QifActivityLogInputMsg> result = new LinkedList<QifActivityLogInputMsg>();

        loopQueue:
        for (int i = 0; i < maxFetch; i++) {
            QifActivityLogInputMsg log = queue.get();
            if (log != null) {
                result.add(log);
                LOGGER.trace("queue message = {}", log);
            } else {
                break loopQueue;
            }
        }
        LOGGER.trace("total message = {}", result.size());
        return result;
    }

}
