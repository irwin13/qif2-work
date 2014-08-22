package id.co.quadras.qif.engine.job.internal;

import id.co.quadras.qif.engine.QifEngineApplication;
import id.co.quadras.qif.engine.core.QifConstants;
import id.co.quadras.qif.engine.queue.reader.ActivityLogQueueReader;
import id.co.quadras.qif.engine.service.app.AppSettingService;
import id.co.quadras.qif.engine.service.log.ActivityLogService;
import id.co.quadras.qif.model.entity.log.QifActivityLog;
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
public class ActivityLogPersist implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityLogPersist.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        ActivityLogQueueReader queueReader = QifEngineApplication.getInjector().getInstance(ActivityLogQueueReader.class);
        ActivityLogService service = QifEngineApplication.getInjector().getInstance(ActivityLogService.class);

        AppSettingService appSettingService = QifEngineApplication.getInjector().getInstance(AppSettingService.class);
        int maxFetch = QifConstants.DEFAULT_LOG_FETCH;

        try {
            String max = appSettingService.selectByCode("queue.activityLog.maxFetch").getStringValue();
            maxFetch = Integer.valueOf(max);
        } catch (NumberFormatException e) {
            LOGGER.error(e.getLocalizedMessage());
        }

        List<QifActivityLog> list = queueReader.getLogList(maxFetch);
        LOGGER.trace("maxFetch = {}", maxFetch);
        LOGGER.trace("list size = {}", list.size());

        if (!list.isEmpty()) {
            service.batchInsert(list);
        }

    }
}
