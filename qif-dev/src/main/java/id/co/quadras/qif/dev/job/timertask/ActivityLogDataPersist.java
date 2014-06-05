package id.co.quadras.qif.dev.job.timertask;

import com.google.inject.Inject;
import com.irwin13.winwork.basic.config.WinWorkConfig;
import id.co.quadras.qif.dev.service.log.ActivityLogDataService;
import id.co.quadras.qif.core.helper.queue.reader.ActivityLogDataQueueReader;
import id.co.quadras.qif.core.model.entity.log.QifActivityLogData;

import java.util.List;
import java.util.TimerTask;

/**
 * @author irwin Timestamp : 17/05/2014 22:25
 */
public class ActivityLogDataPersist extends TimerTask {

    private final ActivityLogDataQueueReader queueReader;
    private final ActivityLogDataService service;
    private final WinWorkConfig config;

    @Inject
    public ActivityLogDataPersist(ActivityLogDataQueueReader queueReader, ActivityLogDataService service, WinWorkConfig config) {
        this.queueReader = queueReader;
        this.service = service;
        this.config = config;
    }

    @Override
    public void run() {
        int maxFetch = config.getInt("queue.activityLogData.maxFetch", 10);
        List<QifActivityLogData> list = queueReader.getLogList(maxFetch);
        service.batchInsert(list);
    }
}
