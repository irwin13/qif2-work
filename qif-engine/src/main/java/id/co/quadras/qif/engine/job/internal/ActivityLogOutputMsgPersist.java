package id.co.quadras.qif.engine.job.internal;

import com.irwin13.winwork.basic.config.WinWorkConfig;
import id.co.quadras.qif.core.helper.queue.reader.ActivityLogOutputMsgQueueReader;
import id.co.quadras.qif.core.model.entity.log.QifActivityLogOutputMsg;
import id.co.quadras.qif.engine.guice.GuiceFactory;
import id.co.quadras.qif.engine.service.log.ActivityLogOutputMsgService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

/**
 * @author irwin Timestamp : 17/05/2014 22:25
 */
@DisallowConcurrentExecution
public class ActivityLogOutputMsgPersist implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        ActivityLogOutputMsgQueueReader queueReader = GuiceFactory.getInjector().getInstance(ActivityLogOutputMsgQueueReader.class);
        ActivityLogOutputMsgService service = GuiceFactory.getInjector().getInstance(ActivityLogOutputMsgService.class);
        WinWorkConfig config = GuiceFactory.getInjector().getInstance(WinWorkConfig.class);

        int maxFetch = config.getInt("queue.activityLogOutputMessage.maxFetch", 10);
        List<QifActivityLogOutputMsg> list = queueReader.getLogList(maxFetch);
        service.batchInsert(list);
    }
}
