package id.co.quadras.qif.engine.job.timertask;

import com.google.inject.Inject;
import com.irwin13.winwork.basic.config.WinWorkConfig;
import id.co.quadras.qif.core.helper.queue.reader.ActivityLogInputMsgQueueReader;
import id.co.quadras.qif.core.model.entity.log.QifActivityLogInputMsg;
import id.co.quadras.qif.engine.service.log.ActivityLogInputMessageService;

import java.util.List;
import java.util.TimerTask;

/**
 * @author irwin Timestamp : 17/05/2014 22:25
 */
public class ActivityLogInputMessagePersist extends TimerTask {

    private final ActivityLogInputMsgQueueReader queueReader;
    private final ActivityLogInputMessageService service;
    private final WinWorkConfig config;

    @Inject
    public ActivityLogInputMessagePersist(ActivityLogInputMsgQueueReader queueReader, ActivityLogInputMessageService service, WinWorkConfig config) {
        this.queueReader = queueReader;
        this.service = service;
        this.config = config;
    }

    @Override
    public void run() {
        int maxFetch = config.getInt("queue.activityLogInputMessage.maxFetch", 10);
        List<QifActivityLogInputMsg> list = queueReader.getLogList(maxFetch);
        service.batchInsert(list);
    }
}
