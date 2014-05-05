package id.co.quadras.qif.helper.queue.reader.imp;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import id.co.quadras.qif.helper.queue.EventLogQueue;
import id.co.quadras.qif.helper.queue.reader.EventLogQueueReader;
import id.co.quadras.qif.model.entity.log.QifEventLog;

import java.util.LinkedList;
import java.util.List;

/**
 * @author irwin Timestamp : 05/05/2014 11:52
 */
public class EventLogQueueReaderImp implements EventLogQueueReader {

    private final EventLogQueue queue;

    @Inject
    public EventLogQueueReaderImp(EventLogQueue queue) {
        this.queue = queue;
    }

    @Override
    public List<QifEventLog> getLogList(int maxFetch) {
        Preconditions.checkArgument(maxFetch <= 0, "maxFetch cannot be less than or equals 0");
        List<QifEventLog> result = new LinkedList<QifEventLog>();

        loopQueue:
        for (int i = 0; i < maxFetch; i++) {
            QifEventLog log = queue.get();
            if (log != null) {
                result.add(log);
            } else {
                break loopQueue;
            }
        }

        return result;
    }

}
