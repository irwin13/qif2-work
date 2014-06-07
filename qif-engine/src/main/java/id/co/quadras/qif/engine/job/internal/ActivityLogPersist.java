package id.co.quadras.qif.engine.job.internal;

import com.irwin13.winwork.basic.config.WinWorkConfig;
import id.co.quadras.qif.core.helper.queue.reader.ActivityLogQueueReader;
import id.co.quadras.qif.core.model.entity.log.QifActivityLog;
import id.co.quadras.qif.engine.guice.GuiceFactory;
import id.co.quadras.qif.engine.service.log.ActivityLogService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

/**
 * @author irwin Timestamp : 17/05/2014 22:25
 */
@DisallowConcurrentExecution
public class ActivityLogPersist implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        ActivityLogQueueReader queueReader = GuiceFactory.getInjector().getInstance(ActivityLogQueueReader.class);
        ActivityLogService service = GuiceFactory.getInjector().getInstance(ActivityLogService.class);
        WinWorkConfig config = GuiceFactory.getInjector().getInstance(WinWorkConfig.class);

        int maxFetch = config.getInt("queue.activityLog.maxFetch", 10);
        List<QifActivityLog> list = queueReader.getLogList(maxFetch);
        service.batchInsert(list);
    }
}
