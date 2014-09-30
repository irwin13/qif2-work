package id.co.quadras.qif.engine.queue.reader.imp;

import id.co.quadras.qif.engine.queue.EventLogQueue;
import id.co.quadras.qif.engine.queue.reader.EventLogQueueReader;
import id.co.quadras.qif.model.entity.log.QifEventLog;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;

/**
 * @author irwin Timestamp : 05/05/2014 11:52
 */
public class EventLogQueueReaderImp implements EventLogQueueReader {

    private final EventLogQueue queue;
    private static final Logger LOGGER = LoggerFactory.getLogger(EventLogQueueReaderImp.class);

    @Inject
    public EventLogQueueReaderImp(EventLogQueue queue) {
        this.queue = queue;
    }

    @Override
    public List<QifEventLog> getLogList(int maxFetch) {
        Preconditions.checkArgument(maxFetch > 0, "maxFetch cannot be less than or equals 0");
        List<QifEventLog> result = new LinkedList<QifEventLog>();

        loopQueue:
        for (int i = 0; i < maxFetch; i++) {
            QifEventLog log = queue.get();
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
	public List<QifEventLog> drainQueue() {
		List<QifEventLog> result = new LinkedList<QifEventLog>();
		
		drainLoop:
		while (true) {
			QifEventLog log = queue.get();
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
