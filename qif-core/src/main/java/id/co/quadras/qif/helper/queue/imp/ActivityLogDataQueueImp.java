package id.co.quadras.qif.helper.queue.imp;

import id.co.quadras.qif.helper.queue.ActivityLogDataQueue;
import id.co.quadras.qif.model.entity.log.QifActivityLogData;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author irwin Timestamp : 29/04/2014 18:58
 */
public class ActivityLogDataQueueImp implements ActivityLogDataQueue {

    private static final ConcurrentLinkedQueue<QifActivityLogData> QUEUE = new ConcurrentLinkedQueue<QifActivityLogData>();

    @Override
    public boolean put(QifActivityLogData qifActivityLogData) {
        return QUEUE.offer(qifActivityLogData);
    }

    @Override
    public QifActivityLogData get() {
        return QUEUE.poll();
    }

    @Override
    public int size() {
        return QUEUE.size();
    }

}
