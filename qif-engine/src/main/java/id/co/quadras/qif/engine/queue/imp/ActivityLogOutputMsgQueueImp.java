package id.co.quadras.qif.engine.queue.imp;

import id.co.quadras.qif.engine.queue.ActivityLogOutputMsgQueue;
import id.co.quadras.qif.model.entity.log.QifActivityLogOutputMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author irwin Timestamp : 29/04/2014 18:59
 */
public class ActivityLogOutputMsgQueueImp implements ActivityLogOutputMsgQueue {

    private static final ConcurrentLinkedQueue<QifActivityLogOutputMsg> QUEUE = new ConcurrentLinkedQueue<QifActivityLogOutputMsg>();
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityLogOutputMsgQueueImp.class);

    @Override
    public boolean put(QifActivityLogOutputMsg qifActivityLogOutputMsg) {
        LOGGER.trace("put message = {}", qifActivityLogOutputMsg);
        return QUEUE.offer(qifActivityLogOutputMsg);
    }

    @Override
    public QifActivityLogOutputMsg get() {
        return QUEUE.poll();
    }

    @Override
    public int size() {
        return QUEUE.size();
    }

}
