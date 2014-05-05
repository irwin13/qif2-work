package id.co.quadras.qif.helper.queue.imp;

import id.co.quadras.qif.helper.queue.EventLogQueue;
import id.co.quadras.qif.model.entity.log.QifEventLog;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author irwin Timestamp : 29/04/2014 18:50
 */
public class EventLogQueueImp implements EventLogQueue {

    private static final ConcurrentLinkedQueue<QifEventLog> QUEUE = new ConcurrentLinkedQueue<QifEventLog>();

    @Override
    public boolean put(QifEventLog qifEventLog) {
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
