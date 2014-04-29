package id.co.quadras.qif.helper.queue.imp;

import id.co.quadras.qif.helper.queue.QifActivityLogQueue;
import id.co.quadras.qif.model.entity.log.QifActivityLog;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author irwin Timestamp : 29/04/2014 18:58
 */
public class QifActivityLogQueueImp implements QifActivityLogQueue {

    private static final ConcurrentLinkedQueue<QifActivityLog> QUEUE = new ConcurrentLinkedQueue<QifActivityLog>();

    @Override
    public boolean put(QifActivityLog qifActivityLog) {
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
