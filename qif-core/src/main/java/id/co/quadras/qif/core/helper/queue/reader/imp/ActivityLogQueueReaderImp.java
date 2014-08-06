package id.co.quadras.qif.core.helper.queue.reader.imp;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import id.co.quadras.qif.core.helper.queue.ActivityLogQueue;
import id.co.quadras.qif.core.helper.queue.reader.ActivityLogQueueReader;
import id.co.quadras.qif.core.model.entity.log.QifActivityLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * @author irwin Timestamp : 05/05/2014 11:52
 */
public class ActivityLogQueueReaderImp implements ActivityLogQueueReader {

    private final ActivityLogQueue queue;
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityLogQueueReaderImp.class);

    @Inject
    public ActivityLogQueueReaderImp(ActivityLogQueue queue) {
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
