package id.co.quadras.qif.dev.job.timertask;

import com.google.inject.Inject;
import com.irwin13.winwork.basic.config.WinWorkConfig;
import id.co.quadras.qif.dev.service.log.ActivityLogInputMessageService;
import id.co.quadras.qif.helper.queue.reader.ActivityLogInputMessageQueueReader;
import id.co.quadras.qif.model.entity.log.QifActivityLogInputMessage;

import java.util.List;
import java.util.TimerTask;

/**
 * @author irwin Timestamp : 17/05/2014 22:25
 */
public class ActivityLogInputMessagePersist extends TimerTask {

    private final ActivityLogInputMessageQueueReader queueReader;
    private final ActivityLogInputMessageService service;
    private final WinWorkConfig config;

    @Inject
    public ActivityLogInputMessagePersist(ActivityLogInputMessageQueueReader queueReader, ActivityLogInputMessageService service, WinWorkConfig config) {
        this.queueReader = queueReader;
        this.service = service;
        this.config = config;
    }

    @Override
    public void run() {
        int maxFetch = config.getInt("queue.activityLogInputMessage.maxFetch", 10);
        List<QifActivityLogInputMessage> list = queueReader.getLogList(maxFetch);
        service.batchInsert(list);
    }
}
