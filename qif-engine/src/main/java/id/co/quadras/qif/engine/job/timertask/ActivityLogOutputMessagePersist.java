package id.co.quadras.qif.engine.job.timertask;

import com.google.inject.Inject;
import com.irwin13.winwork.basic.config.WinWorkConfig;
import id.co.quadras.qif.core.helper.queue.reader.ActivityLogOutputMsgQueueReader;
import id.co.quadras.qif.core.model.entity.log.QifActivityLogOutputMsg;
import id.co.quadras.qif.engine.service.log.ActivityLogOutputMessageService;

import java.util.List;
import java.util.TimerTask;

/**
 * @author irwin Timestamp : 17/05/2014 22:25
 */
public class ActivityLogOutputMessagePersist extends TimerTask {

    private final ActivityLogOutputMsgQueueReader queueReader;
    private final ActivityLogOutputMessageService service;
    private final WinWorkConfig config;

    @Inject
    public ActivityLogOutputMessagePersist(ActivityLogOutputMsgQueueReader queueReader, ActivityLogOutputMessageService service, WinWorkConfig config) {
        this.queueReader = queueReader;
        this.service = service;
        this.config = config;
    }

    @Override
    public void run() {
        int maxFetch = config.getInt("queue.activityLogOutputMessage.maxFetch", 10);
        List<QifActivityLogOutputMsg> list = queueReader.getLogList(maxFetch);
        service.batchInsert(list);
    }
}
