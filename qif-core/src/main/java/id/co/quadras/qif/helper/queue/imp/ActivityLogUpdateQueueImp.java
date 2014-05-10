package id.co.quadras.qif.helper.queue.imp;

import id.co.quadras.qif.helper.queue.ActivityLogUpdateQueue;
import id.co.quadras.qif.model.entity.log.QifActivityLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author irwin Timestamp : 10/05/2014 23:26
 */
public class ActivityLogUpdateQueueImp implements ActivityLogUpdateQueue {

    private static final ConcurrentLinkedQueue<QifActivityLog> QUEUE = new ConcurrentLinkedQueue<QifActivityLog>();
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityLogUpdateQueueImp.class);

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
