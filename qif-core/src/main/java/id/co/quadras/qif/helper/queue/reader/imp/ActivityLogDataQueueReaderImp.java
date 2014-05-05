package id.co.quadras.qif.helper.queue.reader.imp;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import id.co.quadras.qif.helper.queue.ActivityLogDataQueue;
import id.co.quadras.qif.helper.queue.reader.ActivityLogDataQueueReader;
import id.co.quadras.qif.model.entity.log.QifActivityLogData;

import java.util.LinkedList;
import java.util.List;

/**
 * @author irwin Timestamp : 05/05/2014 11:51
 */
public class ActivityLogDataQueueReaderImp implements ActivityLogDataQueueReader {

    private final ActivityLogDataQueue queue;

    @Inject
    public ActivityLogDataQueueReaderImp(ActivityLogDataQueue queue) {
        this.queue = queue;
    }

    @Override
    public List<QifActivityLogData> getLogList(int maxFetch) {
        Preconditions.checkArgument(maxFetch <= 0, "maxFetch cannot be less than or equals 0");
        List<QifActivityLogData> result = new LinkedList<QifActivityLogData>();

        loopQueue:
        for (int i = 0; i < maxFetch; i++) {
            QifActivityLogData log = queue.get();
            if (log != null) {
                result.add(log);
            } else {
                break loopQueue;
            }
        }

        return result;
    }
}
