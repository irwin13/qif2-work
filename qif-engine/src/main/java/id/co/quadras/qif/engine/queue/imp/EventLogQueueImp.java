package id.co.quadras.qif.engine.queue.imp;

import id.co.quadras.qif.engine.queue.EventLogQueue;
import id.co.quadras.qif.model.entity.log.QifEventLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author irwin Timestamp : 29/04/2014 18:50
 */
public class EventLogQueueImp implements EventLogQueue {

    private static final ConcurrentLinkedQueue<QifEventLog> QUEUE = new ConcurrentLinkedQueue<QifEventLog>();
    private static final Logger LOGGER = LoggerFactory.getLogger(EventLogQueueImp.class);

    @Override
    public boolean put(QifEventLog qifEventLog) {
        LOGGER.trace("put message = {}", qifEventLog);
        return QUEUE.offer(qifEventLog);
    }

    @Override
    public QifEventLog get() {
        return QUEUE.poll();
    }

    @Override
    public int size() {
        return QUEUE.size();
    }
}
