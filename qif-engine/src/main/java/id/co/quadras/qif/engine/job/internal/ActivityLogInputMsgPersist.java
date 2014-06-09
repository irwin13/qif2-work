package id.co.quadras.qif.engine.job.internal;

import id.co.quadras.qif.core.QifConstants;
import id.co.quadras.qif.core.helper.queue.reader.ActivityLogInputMsgQueueReader;
import id.co.quadras.qif.core.model.entity.log.QifActivityLogInputMsg;
import id.co.quadras.qif.engine.guice.GuiceFactory;
import id.co.quadras.qif.engine.service.app.AppSettingService;
import id.co.quadras.qif.engine.service.log.ActivityLogInputMsgService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author irwin Timestamp : 17/05/2014 22:25
 */
@DisallowConcurrentExecution
public class ActivityLogInputMsgPersist implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityLogInputMsgPersist.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        ActivityLogInputMsgQueueReader queueReader = GuiceFactory.getInjector().getInstance(ActivityLogInputMsgQueueReader.class);
        ActivityLogInputMsgService service = GuiceFactory.getInjector().getInstance(ActivityLogInputMsgService.class);

        AppSettingService appSettingService = GuiceFactory.getInjector().getInstance(AppSettingService.class);
        int maxFetch = QifConstants.DEFAULT_LOG_FETCH;

        try {
            maxFetch = Integer.valueOf(appSettingService.selectByCode("queue.activityLogInputMessage.maxFetch").getCode());
        } catch (NumberFormatException e) {
            LOGGER.error(e.getLocalizedMessage());
        } catch (NullPointerException e) {
            LOGGER.error(e.getLocalizedMessage());
        }

        List<QifActivityLogInputMsg> list = queueReader.getLogList(maxFetch);
        service.batchInsert(list);
    }
}
