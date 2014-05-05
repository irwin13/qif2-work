package id.co.quadras.qif.helper.queue.reader.imp;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import id.co.quadras.qif.helper.queue.ActivityLogInputMessageQueue;
import id.co.quadras.qif.helper.queue.reader.ActivityLogInputMessageQueueReader;
import id.co.quadras.qif.model.entity.log.QifActivityLogInputMessage;

import java.util.LinkedList;
import java.util.List;

/**
 * @author irwin Timestamp : 05/05/2014 11:51
 */
public class ActivityLogInputMessageQueueReaderImp implements ActivityLogInputMessageQueueReader {

    private final ActivityLogInputMessageQueue queue;

    @Inject
    public ActivityLogInputMessageQueueReaderImp(ActivityLogInputMessageQueue queue) {
        this.queue = queue;
    }

    @Override
    public List<QifActivityLogInputMessage> getLogList(int maxFetch) {
        Preconditions.checkArgument(maxFetch <= 0, "maxFetch cannot be less than or equals 0");
        List<QifActivityLogInputMessage> result = new LinkedList<QifActivityLogInputMessage>();

        loopQueue:
        for (int i = 0; i < maxFetch; i++) {
            QifActivityLogInputMessage log = queue.get();
            if (log != null) {
                result.add(log);
            } else {
                break loopQueue;
            }
        }

        return result;
    }

}
