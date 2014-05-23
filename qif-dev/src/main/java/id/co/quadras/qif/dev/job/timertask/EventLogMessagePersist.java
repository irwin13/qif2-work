package id.co.quadras.qif.dev.job.timertask;

import com.google.inject.Inject;
import com.irwin13.winwork.basic.config.WinWorkConfig;
import id.co.quadras.qif.dev.service.log.EventLogMessageService;
import id.co.quadras.qif.helper.queue.reader.EventLogMsgQueueReader;
import id.co.quadras.qif.model.entity.log.QifEventLogMsg;

import java.util.List;
import java.util.TimerTask;

/**
 * @author irwin Timestamp : 17/05/2014 22:22
 */
public class EventLogMessagePersist extends TimerTask {

    private final EventLogMsgQueueReader queueReader;
    private final EventLogMessageService service;
    private final WinWorkConfig config;

    @Inject
    public EventLogMessagePersist(EventLogMsgQueueReader queueReader, EventLogMessageService service, WinWorkConfig config) {
        this.queueReader = queueReader;
        this.service = service;
        this.config = config;
    }

    @Override
    public void run() {
        int maxFetch = config.getInt("queue.eventLogMessage.maxFetch", 10);
        List<QifEventLogMsg> list = queueReader.getLogList(maxFetch);
        service.batchInsert(list);
    }
}
