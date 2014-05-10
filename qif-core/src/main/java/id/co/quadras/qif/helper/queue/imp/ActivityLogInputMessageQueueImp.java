package id.co.quadras.qif.helper.queue.imp;

import id.co.quadras.qif.helper.queue.ActivityLogInputMessageQueue;
import id.co.quadras.qif.model.entity.log.QifActivityLogInputMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author irwin Timestamp : 29/04/2014 18:58
 */
public class ActivityLogInputMessageQueueImp implements ActivityLogInputMessageQueue {

    private static final ConcurrentLinkedQueue<QifActivityLogInputMessage> QUEUE = new ConcurrentLinkedQueue<QifActivityLogInputMessage>();
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityLogInputMessageQueueImp.class);

    @Override
    public boolean put(QifActivityLogInputMessage qifActivityLogInputMessage) {
        LOGGER.trace("put message = {}", qifActivityLogInputMessage);
        return QUEUE.offer(qifActivityLogInputMessage);
    }

    @Override
    public QifActivityLogInputMessage get() {
        return QUEUE.poll();
    }

    @Override
    public int size() {
        return QUEUE.size();
    }

}
