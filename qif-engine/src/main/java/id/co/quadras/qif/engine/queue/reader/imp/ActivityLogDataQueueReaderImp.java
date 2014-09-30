package id.co.quadras.qif.engine.queue.reader.imp;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import id.co.quadras.qif.engine.queue.ActivityLogDataQueue;
import id.co.quadras.qif.engine.queue.reader.ActivityLogDataQueueReader;
import id.co.quadras.qif.model.entity.log.QifActivityLogData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * @author irwin Timestamp : 05/05/2014 11:51
 */
public class ActivityLogDataQueueReaderImp implements ActivityLogDataQueueReader {

    private final ActivityLogDataQueue queue;
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityLogDataQueueReaderImp.class);

    @Inject
    public ActivityLogDataQueueReaderImp(ActivityLogDataQueue queue) {
        this.queue = queue;
    }

    @Override
    public List<QifActivityLogData> getLogList(int maxFetch) {
        Preconditions.checkArgument(maxFetch > 0, "maxFetch cannot be less than or equals 0");
        List<QifActivityLogData> result = new LinkedList<QifActivityLogData>();

        loopQueue:
        for (int i = 0; i < maxFetch; i++) {
            QifActivityLogData log = queue.get();
            if (log != null) {
                LOGGER.trace("queue message = {}", log);
                result.add(log);
            } else {
                break loopQueue;
            }
        }
        LOGGER.trace("total message = {}", result.size());
        return result;
    }

	@Override
	public List<QifActivityLogData> drainQueue() {
		List<QifActivityLogData> result = new LinkedList<QifActivityLogData>();
		
		drainLoop:
		while (true) {
			QifActivityLogData log = queue.get();
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
