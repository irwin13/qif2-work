package id.co.quadras.qif.engine.queue.imp;

import id.co.quadras.qif.engine.queue.ActivityLogInputMsgQueue;
import id.co.quadras.qif.model.entity.log.QifActivityLogInputMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author irwin Timestamp : 29/04/2014 18:58
 */
public class ActivityLogInputMsgQueueImp implements ActivityLogInputMsgQueue {

    private static final ConcurrentLinkedQueue<QifActivityLogInputMsg> QUEUE = new ConcurrentLinkedQueue<QifActivityLogInputMsg>();
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityLogInputMsgQueueImp.class);

    @Override
    public boolean put(QifActivityLogInputMsg qifActivityLogInputMsg) {
        LOGGER.trace("put message = {}", qifActivityLogInputMsg);
        return QUEUE.offer(qifActivityLogInputMsg);
    }

    @Override
    public QifActivityLogInputMsg get() {
        return QUEUE.poll();
    }

    @Override
    public int size() {
        return QUEUE.size();
    }

}
