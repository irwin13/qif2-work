package id.co.quadras.qif.engine.job.internal;

import com.irwin13.winwork.basic.config.WinWorkConfig;
import id.co.quadras.qif.core.helper.queue.reader.ActivityLogDataQueueReader;
import id.co.quadras.qif.core.model.entity.log.QifActivityLogData;
import id.co.quadras.qif.engine.guice.GuiceFactory;
import id.co.quadras.qif.engine.service.log.ActivityLogDataService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

/**
 * @author irwin Timestamp : 17/05/2014 22:25
 */
@DisallowConcurrentExecution
public class ActivityLogDataPersist implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        ActivityLogDataQueueReader queueReader = GuiceFactory.getInjector().getInstance(ActivityLogDataQueueReader.class);
        ActivityLogDataService service = GuiceFactory.getInjector().getInstance(ActivityLogDataService.class);
        WinWorkConfig config = GuiceFactory.getInjector().getInstance(WinWorkConfig.class);

        int maxFetch = config.getInt("queue.activityLogData.maxFetch", 10);
        List<QifActivityLogData> list = queueReader.getLogList(maxFetch);
        service.batchInsert(list);
    }
}
