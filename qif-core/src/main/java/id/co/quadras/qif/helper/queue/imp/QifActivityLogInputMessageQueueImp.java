package id.co.quadras.qif.helper.queue.imp;

import id.co.quadras.qif.helper.queue.QifActivityLogInputMessageQueue;
import id.co.quadras.qif.model.entity.log.QifActivityLogInputMessage;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author irwin Timestamp : 29/04/2014 18:58
 */
public class QifActivityLogInputMessageQueueImp implements QifActivityLogInputMessageQueue {

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
