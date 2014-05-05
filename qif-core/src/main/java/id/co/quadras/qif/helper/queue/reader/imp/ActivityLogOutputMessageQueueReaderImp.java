package id.co.quadras.qif.helper.queue.reader.imp;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import id.co.quadras.qif.helper.queue.ActivityLogOutputMessageQueue;
import id.co.quadras.qif.helper.queue.reader.ActivityLogOutputMessageQueueReader;
import id.co.quadras.qif.model.entity.log.QifActivityLogOutputMessage;

import java.util.LinkedList;
import java.util.List;

/**
 * @author irwin Timestamp : 05/05/2014 11:52
 */
public class ActivityLogOutputMessageQueueReaderImp implements ActivityLogOutputMessageQueueReader {

    private final ActivityLogOutputMessageQueue queue;

    @Inject
    public ActivityLogOutputMessageQueueReaderImp(ActivityLogOutputMessageQueue queue) {
        this.queue = queue;
    }

    @Override
    public List<QifActivityLogOutputMessage> getLogList(int maxFetch) {
        Preconditions.checkArgument(maxFetch > 0, "maxFetch cannot be less than or equals 0");
        List<QifActivityLogOutputMessage> result = new LinkedList<QifActivityLogOutputMessage>();

        loopQueue:
        for (int i = 0; i < maxFetch; i++) {
            QifActivityLogOutputMessage log = queue.get();
            if (log != null) {
                result.add(log);
            } else {
                break loopQueue;
            }
        }

        return result;
    }
}
