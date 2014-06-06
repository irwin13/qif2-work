package id.co.quadras.qif.engine.job.timertask;

import com.google.inject.Inject;
import com.irwin13.winwork.basic.config.WinWorkConfig;
import id.co.quadras.qif.core.helper.queue.reader.EventLogQueueReader;
import id.co.quadras.qif.core.model.entity.log.QifEventLog;
import id.co.quadras.qif.engine.service.log.EventLogService;

import java.util.List;
import java.util.TimerTask;

/**
 * @author irwin Timestamp : 17/05/2014 20:57
 */
public class EventLogPersist extends TimerTask {

    private final EventLogQueueReader queueReader;
    private final EventLogService service;
    private final WinWorkConfig config;

    @Inject
    public EventLogPersist(EventLogQueueReader queueReader, EventLogService service, WinWorkConfig config) {
        this.queueReader = queueReader;
        this.service = service;
        this.config = config;
    }

    @Override
    public void run() {
        int maxFetch = config.getInt("queue.eventLog.maxFetch", 10);
        List<QifEventLog> list = queueReader.getLogList(maxFetch);
        service.batchInsert(list);
    }
}