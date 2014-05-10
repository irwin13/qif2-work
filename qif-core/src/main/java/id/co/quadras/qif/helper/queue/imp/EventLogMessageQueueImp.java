package id.co.quadras.qif.helper.queue.imp;

import id.co.quadras.qif.helper.queue.EventLogMessageQueue;
import id.co.quadras.qif.model.entity.log.QifEventLogMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author irwin Timestamp : 29/04/2014 18:57
 */
public class EventLogMessageQueueImp implements EventLogMessageQueue {

    private static final ConcurrentLinkedQueue<QifEventLogMessage> QUEUE = new ConcurrentLinkedQueue<QifEventLogMessage>();
    private static final Logger LOGGER = LoggerFactory.getLogger(EventLogMessageQueueImp.class);

    @Override
    public boolean put(QifEventLogMessage qifEventLogMessage) {
        LOGGER.trace("put message = {}", qifEventLogMessage);
        return QUEUE.offer(qifEventLogMessage);
    }

    @Override
    public QifEventLogMessage get() {
        return QUEUE.poll();
    }

    @Override
    public int size() {
        return QUEUE.size();
    }

}
