package id.co.quadras.qif.engine.job.internal;

import com.irwin13.winwork.basic.model.entity.app.AppSetting;
import id.co.quadras.qif.engine.core.QifConstants;
import id.co.quadras.qif.engine.guice.QifGuice;
import id.co.quadras.qif.engine.queue.reader.ActivityLogDataQueueReader;
import id.co.quadras.qif.engine.service.app.AppSettingService;
import id.co.quadras.qif.engine.service.log.ActivityLogDataService;
import id.co.quadras.qif.model.entity.log.QifActivityLogData;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.TimerTask;

/**
 * @author irwin Timestamp : 17/05/2014 22:25
 */
@DisallowConcurrentExecution
public class ActivityLogDataPersist extends TimerTask implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityLogDataPersist.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        process();
    }

    @Override
    public void run() {
        process();
    }

    private void process() {
        ActivityLogDataQueueReader queueReader = QifGuice.getInjector().getInstance(ActivityLogDataQueueReader.class);
        ActivityLogDataService service = QifGuice.getInjector().getInstance(ActivityLogDataService.class);

        AppSettingService appSettingService = QifGuice.getInjector().getInstance(AppSettingService.class);
        int maxFetch = QifConstants.DEFAULT_LOG_FETCH;

        try {
            AppSetting fetchSetting = appSettingService.selectByCode("queue.activityLogData.maxFetch");
            if (fetchSetting != null) {
                maxFetch = Integer.valueOf(fetchSetting.getStringValue());
            }
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
