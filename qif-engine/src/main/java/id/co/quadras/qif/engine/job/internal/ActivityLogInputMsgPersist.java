package id.co.quadras.qif.engine.job.internal;

import com.irwin13.winwork.basic.config.WinWorkConfig;
import id.co.quadras.qif.core.helper.queue.reader.ActivityLogInputMsgQueueReader;
import id.co.quadras.qif.core.model.entity.log.QifActivityLogInputMsg;
import id.co.quadras.qif.engine.guice.GuiceFactory;
import id.co.quadras.qif.engine.service.log.ActivityLogInputMsgService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

/**
 * @author irwin Timestamp : 17/05/2014 22:25
 */
@DisallowConcurrentExecution
public class ActivityLogInputMsgPersist implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        ActivityLogInputMsgQueueReader queueReader = GuiceFactory.getInjector().getInstance(ActivityLogInputMsgQueueReader.class);
        ActivityLogInputMsgService service = GuiceFactory.getInjector().getInstance(ActivityLogInputMsgService.class);
        WinWorkConfig config = GuiceFactory.getInjector().getInstance(WinWorkConfig.class);

        int maxFetch = config.getInt("queue.activityLogInputMessage.maxFetch", 10);
        List<QifActivityLogInputMsg> list = queueReader.getLogList(maxFetch);
        service.batchInsert(list);
    }
}
