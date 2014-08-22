package id.co.quadras.qif.engine.job.internal;

import id.co.quadras.qif.engine.QifEngineApplication;
import id.co.quadras.qif.engine.core.QifConstants;
import id.co.quadras.qif.engine.guice.QifGuice;
import id.co.quadras.qif.engine.queue.reader.ActivityLogOutputMsgQueueReader;
import id.co.quadras.qif.engine.service.app.AppSettingService;
import id.co.quadras.qif.engine.service.log.ActivityLogOutputMsgService;
import id.co.quadras.qif.model.entity.log.QifActivityLogOutputMsg;
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
public class ActivityLogOutputMsgPersist implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityLogOutputMsgPersist.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        ActivityLogOutputMsgQueueReader queueReader = QifEngineApplication.getInjector().getInstance(ActivityLogOutputMsgQueueReader.class);
        ActivityLogOutputMsgService service = QifEngineApplication.getInjector().getInstance(ActivityLogOutputMsgService.class);

        AppSettingService appSettingService = QifEngineApplication.getInjector().getInstance(AppSettingService.class);
        int maxFetch = QifConstants.DEFAULT_LOG_FETCH;

        try {
            String max = appSettingService.selectByCode("queue.activityLogOutputMsg.maxFetch").getStringValue();
            maxFetch = Integer.valueOf(max);
        } catch (NumberFormatException e) {
            LOGGER.error(e.getLocalizedMessage());
        }

        List<QifActivityLogOutputMsg> list = queueReader.getLogList(maxFetch);
        LOGGER.trace("maxFetch = {}", maxFetch);
        LOGGER.trace("list size = {}", list.size());

        if (!list.isEmpty()) {
            service.batchInsert(list);
        }

    }
}
