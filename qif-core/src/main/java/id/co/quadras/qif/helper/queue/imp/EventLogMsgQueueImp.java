package id.co.quadras.qif.helper.queue.imp;

import id.co.quadras.qif.helper.queue.EventLogMsgQueue;
import id.co.quadras.qif.model.entity.log.QifEventLogMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author irwin Timestamp : 29/04/2014 18:57
 */
public class EventLogMsgQueueImp implements EventLogMsgQueue {

    private static final ConcurrentLinkedQueue<QifEventLogMsg> QUEUE = new ConcurrentLinkedQueue<QifEventLogMsg>();
    private static final Logger LOGGER = LoggerFactory.getLogger(EventLogMsgQueueImp.class);

    @Override
    public boolean put(QifEventLogMsg qifEventLogMsg) {
        LOGGER.trace("put message = {}", qifEventLogMsg);
        return QUEUE.offer(qifEventLogMsg);
    }

    @Override
    public QifEventLogMsg get() {
        return QUEUE.poll();
    }

    @Override
    public int size() {
        return QUEUE.size();
    }

}
