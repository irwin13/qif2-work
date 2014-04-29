package id.co.quadras.qif.helper.queue.imp;

import id.co.quadras.qif.helper.queue.QifEventLogMessageQueue;
import id.co.quadras.qif.model.entity.log.QifEventLogMessage;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author irwin Timestamp : 29/04/2014 18:57
 */
public class QifEventLogMessageQueueImp implements QifEventLogMessageQueue {

    private static final ConcurrentLinkedQueue<QifEventLogMessage> QUEUE = new ConcurrentLinkedQueue<QifEventLogMessage>();

    @Override
    public boolean put(QifEventLogMessage qifEventLogMessage) {
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
