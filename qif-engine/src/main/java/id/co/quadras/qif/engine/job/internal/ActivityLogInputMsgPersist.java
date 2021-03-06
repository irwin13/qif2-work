package id.co.quadras.qif.engine.job.internal;

import com.irwin13.winwork.basic.model.entity.app.AppSetting;
import id.co.quadras.qif.engine.core.QifConstants;
import id.co.quadras.qif.engine.guice.QifGuice;
import id.co.quadras.qif.engine.queue.reader.ActivityLogInputMsgQueueReader;
import id.co.quadras.qif.engine.service.app.AppSettingService;
import id.co.quadras.qif.engine.service.log.ActivityLogInputMsgService;
import id.co.quadras.qif.model.entity.log.QifActivityLogInputMsg;
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
public class ActivityLogInputMsgPersist extends TimerTask implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityLogInputMsgPersist.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        process();
    }

    @Override
    public void run() {
        process();
    }

    private void process() {
        ActivityLogInputMsgQueueReader queueReader = QifGuice.getInjector().getInstance(ActivityLogInputMsgQueueReader.class);
        ActivityLogInputMsgService service = QifGuice.getInjector().getInstance(ActivityLogInputMsgService.class);

        AppSettingService appSettingService = QifGuice.getInjector().getInstance(AppSettingService.class);
        int maxFetch = QifConstants.DEFAULT_LOG_FETCH;

        try {
            AppSetting fetchSetting = appSettingService.selectByCode("queue.activityLogInputMsg.maxFetch");
            if (fetchSetting != null) {
                maxFetch = Integer.valueOf(fetchSetting.getStringValue());
            }
        } catch (NumberFormatException e) {
            LOGGER.error(e.getLocalizedMessage());
        }

        List<QifActivityLogInputMsg> list = queueReader.getLogList(maxFetch);
        LOGGER.trace("maxFetch = {}", maxFetch);
        LOGGER.trace("list size = {}", list.size());

        if (!list.isEmpty()) {
            service.batchInsert(list);
        }
    }
}
