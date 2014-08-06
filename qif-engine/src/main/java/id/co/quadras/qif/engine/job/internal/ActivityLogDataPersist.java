package id.co.quadras.qif.engine.job.internal;

import id.co.quadras.qif.core.QifConstants;
import id.co.quadras.qif.core.helper.queue.reader.ActivityLogDataQueueReader;
import id.co.quadras.qif.core.model.entity.log.QifActivityLogData;
import id.co.quadras.qif.engine.guice.EngineFactory;
import id.co.quadras.qif.engine.service.app.AppSettingService;
import id.co.quadras.qif.engine.service.log.ActivityLogDataService;
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
public class ActivityLogDataPersist implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityLogDataPersist.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        ActivityLogDataQueueReader queueReader = EngineFactory.getInjector().getInstance(ActivityLogDataQueueReader.class);
        ActivityLogDataService service = EngineFactory.getInjector().getInstance(ActivityLogDataService.class);

        AppSettingService appSettingService = EngineFactory.getInjector().getInstance(AppSettingService.class);
        int maxFetch = QifConstants.DEFAULT_LOG_FETCH;

        try {
            String max = appSettingService.selectByCode("queue.activityLogData.maxFetch").getStringValue();
            maxFetch = Integer.valueOf(max);
        } catch (NumberFormatException e) {
            LOGGER.error(e.getLocalizedMessage());
        }

        List<QifActivityLogData> list = queueReader.getLogList(maxFetch);
        LOGGER.trace("maxFetch = {}", maxFetch);
        LOGGER.trace("list size = {}", list.size());
        if (!list.isEmpty()) {
            service.batchInsert(list);
        }

    }
}
