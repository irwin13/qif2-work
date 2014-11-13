package id.co.quadras.qif.engine.job.internal;

import com.irwin13.winwork.basic.model.entity.app.AppSetting;
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
import java.util.TimerTask;

/**
 * @author irwin Timestamp : 17/05/2014 22:25
 */
@DisallowConcurrentExecution
public class ActivityLogOutputMsgPersist extends TimerTask implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityLogOutputMsgPersist.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        process();
    }

    @Override
    public void run() {
        process();
    }

    private void process() {
        ActivityLogOutputMsgQueueReader queueReader = QifGuice.getInjector().getInstance(ActivityLogOutputMsgQueueReader.class);
        ActivityLogOutputMsgService service = QifGuice.getInjector().getInstance(ActivityLogOutputMsgService.class);

        AppSettingService appSettingService = QifGuice.getInjector().getInstance(AppSettingService.class);
        int maxFetch = QifConstants.DEFAULT_LOG_FETCH;

        try {
            AppSetting fetchSetting = appSettingService.selectByCode("queue.activityLogOutputMsg.maxFetch");
            if (fetchSetting != null) {
                maxFetch = Integer.valueOf(fetchSetting.getStringValue());
            }
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
