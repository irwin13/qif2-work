package id.co.quadras.qif.engine.queue.reader.imp;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import id.co.quadras.qif.engine.queue.ActivityLogUpdateQueue;
import id.co.quadras.qif.engine.queue.reader.ActivityLogUpdateQueueReader;
import id.co.quadras.qif.model.entity.log.QifActivityLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * @author irwin Timestamp : 10/05/2014 23:27
 */
public class ActivityLogUpdateQueueReaderImp implements ActivityLogUpdateQueueReader {

    private final ActivityLogUpdateQueue queue;
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityLogUpdateQueueReaderImp.class);

    @Inject
    public ActivityLogUpdateQueueReaderImp(ActivityLogUpdateQueue queue) {
        this.queue = queue;
    }

    @Override
    public List<QifActivityLog> getLogList(int maxFetch) {
        Preconditions.checkArgument(maxFetch > 0, "maxFetch cannot be less than or equals 0");
        List<QifActivityLog> result = new LinkedList<QifActivityLog>();

        loopQueue:
        for (int i = 0; i < maxFetch; i++) {
            QifActivityLog log = queue.get();
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

}
