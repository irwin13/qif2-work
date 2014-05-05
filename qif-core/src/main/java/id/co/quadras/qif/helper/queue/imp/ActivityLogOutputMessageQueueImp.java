package id.co.quadras.qif.helper.queue.imp;

import id.co.quadras.qif.helper.queue.ActivityLogOutputMessageQueue;
import id.co.quadras.qif.model.entity.log.QifActivityLogOutputMessage;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author irwin Timestamp : 29/04/2014 18:59
 */
public class ActivityLogOutputMessageQueueImp implements ActivityLogOutputMessageQueue {
    private static final ConcurrentLinkedQueue<QifActivityLogOutputMessage> QUEUE = new ConcurrentLinkedQueue<QifActivityLogOutputMessage>();

    @Override
    public boolean put(QifActivityLogOutputMessage qifActivityLogOutputMessage) {
        return QUEUE.offer(qifActivityLogOutputMessage);
    }

    @Override
    public QifActivityLogOutputMessage get() {
        return QUEUE.poll();
    }

    @Override
    public int size() {
        return QUEUE.size();
    }

}
