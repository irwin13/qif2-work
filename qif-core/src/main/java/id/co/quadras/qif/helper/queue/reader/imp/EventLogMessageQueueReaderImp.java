package id.co.quadras.qif.helper.queue.reader.imp;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import id.co.quadras.qif.helper.queue.EventLogMessageQueue;
import id.co.quadras.qif.helper.queue.reader.EventLogMessageQueueReader;
import id.co.quadras.qif.model.entity.log.QifEventLogMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * @author irwin Timestamp : 05/05/2014 11:53
 */
public class EventLogMessageQueueReaderImp implements EventLogMessageQueueReader {

    private final EventLogMessageQueue queue;
    private static final Logger LOGGER = LoggerFactory.getLogger(EventLogMessageQueueReaderImp.class);

    @Inject
    public EventLogMessageQueueReaderImp(EventLogMessageQueue queue) {
        this.queue = queue;
    }

    @Override
    public List<QifEventLogMessage> getLogList(int maxFetch) {
        Preconditions.checkArgument(maxFetch > 0, "maxFetch cannot be less than or equals 0");
        List<QifEventLogMessage> result = new LinkedList<QifEventLogMessage>();

        loopQueue:
        for (int i = 0; i < maxFetch; i++) {
            QifEventLogMessage log = queue.get();
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
