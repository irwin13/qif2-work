package id.co.quadras.qif.engine.queue.imp;

import id.co.quadras.qif.engine.queue.ActivityLogQueue;
import id.co.quadras.qif.model.entity.log.QifActivityLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author irwin Timestamp : 29/04/2014 18:58
 */
public class ActivityLogQueueImp implements ActivityLogQueue {

    private static final ConcurrentLinkedQueue<QifActivityLog> QUEUE = new ConcurrentLinkedQueue<QifActivityLog>();
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityLogQueueImp.class);

    @Override
    public boolean put(QifActivityLog qifActivityLog) {
        LOGGER.trace("put message = {}", qifActivityLog);
        return QUEUE.offer(qifActivityLog);
    }

    @Override
    public QifActivityLog get() {
        return QUEUE.poll();
    }

    @Override
    public int size() {
        return QUEUE.size();
    }

}
