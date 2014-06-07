package id.co.quadras.qif.engine.job.internal;

import com.irwin13.winwork.basic.config.WinWorkConfig;
import id.co.quadras.qif.core.helper.queue.reader.EventLogQueueReader;
import id.co.quadras.qif.core.model.entity.log.QifEventLog;
import id.co.quadras.qif.engine.guice.GuiceFactory;
import id.co.quadras.qif.engine.service.log.EventLogService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

/**
 * @author irwin Timestamp : 17/05/2014 20:57
 */
@DisallowConcurrentExecution
public class EventLogPersist implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        EventLogQueueReader queueReader = GuiceFactory.getInjector().getInstance(EventLogQueueReader.class);
        EventLogService service = GuiceFactory.getInjector().getInstance(EventLogService.class);
        WinWorkConfig config = GuiceFactory.getInjector().getInstance(WinWorkConfig.class);
        
        int maxFetch = config.getInt("queue.eventLog.maxFetch", 10);
        List<QifEventLog> list = queueReader.getLogList(maxFetch);
        service.batchInsert(list);
    }
}
