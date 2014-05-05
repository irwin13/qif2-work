package id.co.quadras.qif.helper.queue.imp;

import id.co.quadras.qif.helper.queue.ActivityLogInputMessageQueue;
import id.co.quadras.qif.model.entity.log.QifActivityLogInputMessage;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author irwin Timestamp : 29/04/2014 18:58
 */
public class ActivityLogInputMessageQueueImp implements ActivityLogInputMessageQueue {

    private static final ConcurrentLinkedQueue<QifActivityLogInputMessage> QUEUE = new ConcurrentLinkedQueue<QifActivityLogInputMessage>();

    @Override
    public boolean put(QifActivityLogInputMessage qifActivityLogInputMessage) {
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
