package id.co.quadras.qif.engine.job.internal;

import com.irwin13.winwork.basic.config.WinWorkConfig;
import id.co.quadras.qif.core.helper.queue.reader.EventLogMsgQueueReader;
import id.co.quadras.qif.core.model.entity.log.QifEventLogMsg;
import id.co.quadras.qif.engine.guice.GuiceFactory;
import id.co.quadras.qif.engine.service.log.EventLogMsgService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

/**
 * @author irwin Timestamp : 17/05/2014 22:22
 */
@DisallowConcurrentExecution
public class EventLogMsgPersist implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        EventLogMsgQueueReader queueReader = GuiceFactory.getInjector().getInstance(EventLogMsgQueueReader.class);
        EventLogMsgService service = GuiceFactory.getInjector().getInstance(EventLogMsgService.class);
        WinWorkConfig config = GuiceFactory.getInjector().getInstance(WinWorkConfig.class);

        int maxFetch = config.getInt("queue.eventLogMessage.maxFetch", 10);
        List<QifEventLogMsg> list = queueReader.getLogList(maxFetch);
        service.batchInsert(list);
    }
}
