package id.co.quadras.qif.dev.job.timertask;

import com.google.inject.Inject;
import com.irwin13.winwork.basic.config.WinWorkConfig;
import id.co.quadras.qif.dev.service.log.ActivityLogService;
import id.co.quadras.qif.core.helper.queue.reader.ActivityLogQueueReader;
import id.co.quadras.qif.core.model.entity.log.QifActivityLog;

import java.util.List;
import java.util.TimerTask;

/**
 * @author irwin Timestamp : 17/05/2014 22:25
 */
public class ActivityLogPersist extends TimerTask {

    private final ActivityLogQueueReader queueReader;
    private final ActivityLogService service;
    private final WinWorkConfig config;

    @Inject
    public ActivityLogPersist(ActivityLogQueueReader queueReader, ActivityLogService service, WinWorkConfig config) {
        this.queueReader = queueReader;
        this.service = service;
        this.config = config;
    }

    @Override
    public void run() {
        int maxFetch = config.getInt("queue.activityLog.maxFetch", 10);
        List<QifActivityLog> list = queueReader.getLogList(maxFetch);
        service.batchInsert(list);
    }
}
