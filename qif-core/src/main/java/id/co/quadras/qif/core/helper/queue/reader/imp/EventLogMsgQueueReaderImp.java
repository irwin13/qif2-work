package id.co.quadras.qif.core.helper.queue.reader.imp;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import id.co.quadras.qif.core.helper.queue.EventLogMsgQueue;
import id.co.quadras.qif.core.helper.queue.reader.EventLogMsgQueueReader;
import id.co.quadras.qif.core.model.entity.log.QifEventLogMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * @author irwin Timestamp : 05/05/2014 11:53
 */
public class EventLogMsgQueueReaderImp implements EventLogMsgQueueReader {

    private final EventLogMsgQueue queue;
    private static final Logger LOGGER = LoggerFactory.getLogger(EventLogMsgQueueReaderImp.class);

    @Inject
    public EventLogMsgQueueReaderImp(EventLogMsgQueue queue) {
        this.queue = queue;
    }

    @Override
    public List<QifEventLogMsg> getLogList(int maxFetch) {
        Preconditions.checkArgument(maxFetch > 0, "maxFetch cannot be less than or equals 0");
        List<QifEventLogMsg> result = new LinkedList<QifEventLogMsg>();

        loopQueue:
        for (int i = 0; i < maxFetch; i++) {
            QifEventLogMsg log = queue.get();
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
