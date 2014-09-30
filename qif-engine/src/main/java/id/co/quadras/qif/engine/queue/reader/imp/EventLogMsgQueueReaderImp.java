package id.co.quadras.qif.engine.queue.reader.imp;

import id.co.quadras.qif.engine.queue.EventLogMsgQueue;
import id.co.quadras.qif.engine.queue.reader.EventLogMsgQueueReader;
import id.co.quadras.qif.model.entity.log.QifEventLogMsg;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;

/**
 * @author irwin Timestamp : 05/05/2014 11:53
 */
public class EventLogMsgQueueReaderImp implements EventLogMsgQueueReader {

    private final EventLogMsgQueue queue;
    private static final Logger LOGGER = LoggerFactory.getLogger(EventLogMsgQueueReaderImp.class);

    @Inject
    public EventLogMsgQueueReaderImp(EventLogMsgQueue queue) {
        this.queue = queue;
    }

    @Override
    public List<QifEventLogMsg> getLogList(int maxFetch) {
        Preconditions.checkArgument(maxFetch > 0, "maxFetch cannot be less than or equals 0");
        List<QifEventLogMsg> result = new LinkedList<QifEventLogMsg>();

        loopQueue:
        for (int i = 0; i < maxFetch; i++) {
            QifEventLogMsg log = queue.get();
            if (log != null) {
                result.add(log);
                LOGGER.trace("queue message = {}", log);
            } else {
                break loopQueue;
            }
        }
        LOGGER.trace("total message = {}", result.size());
        return result;
    }

	@Override
	public List<QifEventLogMsg> drainQueue() {
		List<QifEventLogMsg> result = new LinkedList<QifEventLogMsg>();
		
		drainLoop:
		while (true) {
			QifEventLogMsg log = queue.get();
			if (log != null) {
				result.add(log);
			} else {
				break drainLoop;
			}
		}
		
		LOGGER.info("total drained message = {}", result.size());
		return result;
	}

}
