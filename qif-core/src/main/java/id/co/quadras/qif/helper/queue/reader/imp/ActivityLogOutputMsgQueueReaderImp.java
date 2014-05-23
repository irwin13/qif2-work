package id.co.quadras.qif.helper.queue.reader.imp;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import id.co.quadras.qif.helper.queue.ActivityLogOutputMsgQueue;
import id.co.quadras.qif.helper.queue.reader.ActivityLogOutputMsgQueueReader;
import id.co.quadras.qif.model.entity.log.QifActivityLogOutputMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * @author irwin Timestamp : 05/05/2014 11:52
 */
public class ActivityLogOutputMsgQueueReaderImp implements ActivityLogOutputMsgQueueReader {

    private final ActivityLogOutputMsgQueue queue;
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityLogOutputMsgQueueReaderImp.class);

    @Inject
    public ActivityLogOutputMsgQueueReaderImp(ActivityLogOutputMsgQueue queue) {
        this.queue = queue;
    }

    @Override
    public List<QifActivityLogOutputMsg> getLogList(int maxFetch) {
        Preconditions.checkArgument(maxFetch > 0, "maxFetch cannot be less than or equals 0");
        List<QifActivityLogOutputMsg> result = new LinkedList<QifActivityLogOutputMsg>();

        loopQueue:
        for (int i = 0; i < maxFetch; i++) {
            QifActivityLogOutputMsg log = queue.get();
            if (log != null) {
                result.add(log);
                LOGGER.trace("queue message = {}", log);
            } else {
                break loopQueue;
            }
        }
        LOGGER.debug("total message = {}", result.size());
        return result;
    }
}
