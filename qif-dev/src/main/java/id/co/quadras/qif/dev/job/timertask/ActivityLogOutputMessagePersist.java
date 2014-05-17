package id.co.quadras.qif.dev.job.timertask;

import com.google.inject.Inject;
import com.irwin13.winwork.basic.config.WinWorkConfig;
import id.co.quadras.qif.dev.service.log.ActivityLogOutputMessageService;
import id.co.quadras.qif.helper.queue.reader.ActivityLogOutputMessageQueueReader;
import id.co.quadras.qif.model.entity.log.QifActivityLogOutputMessage;

import java.util.List;
import java.util.TimerTask;

/**
 * @author irwin Timestamp : 17/05/2014 22:25
 */
public class ActivityLogOutputMessagePersist extends TimerTask {

    private final ActivityLogOutputMessageQueueReader queueReader;
    private final ActivityLogOutputMessageService service;
    private final WinWorkConfig config;

    @Inject
    public ActivityLogOutputMessagePersist(ActivityLogOutputMessageQueueReader queueReader, ActivityLogOutputMessageService service, WinWorkConfig config) {
        this.queueReader = queueReader;
        this.service = service;
        this.config = config;
    }

    @Override
    public void run() {
        int maxFetch = config.getInt("queue.activityLogOutputMessage.maxFetch", 10);
        List<QifActivityLogOutputMessage> list = queueReader.getLogList(maxFetch);
        service.batchInsert(list);
    }
}
