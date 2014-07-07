package id.co.quadras.qif.engine.job.internal;

import id.co.quadras.qif.core.QifConstants;
import id.co.quadras.qif.core.helper.queue.reader.EventLogMsgQueueReader;
import id.co.quadras.qif.core.model.entity.log.QifEventLogMsg;
import id.co.quadras.qif.engine.guice.EngineFactory;
import id.co.quadras.qif.engine.service.app.AppSettingService;
import id.co.quadras.qif.engine.service.log.EventLogMsgService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author irwin Timestamp : 17/05/2014 22:22
 */
@DisallowConcurrentExecution
public class EventLogMsgPersist implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventLogMsgPersist.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        EventLogMsgQueueReader queueReader = EngineFactory.getInjector().getInstance(EventLogMsgQueueReader.class);
        EventLogMsgService service = EngineFactory.getInjector().getInstance(EventLogMsgService.class);
        AppSettingService appSettingService = EngineFactory.getInjector().getInstance(AppSettingService.class);
        int maxFetch = QifConstants.DEFAULT_LOG_FETCH;

        try {
            String max = appSettingService.selectByCode("queue.eventLogMsg.maxFetch").getStringValue();
            maxFetch = Integer.valueOf(max);
        } catch (NumberFormatException e) {
            LOGGER.error(e.getLocalizedMessage());
        }

        List<QifEventLogMsg> list = queueReader.getLogList(maxFetch);
        LOGGER.debug("maxFetch = {}", maxFetch);
        LOGGER.debug("list size = {}", list.size());

        if (!list.isEmpty()) {
            service.batchInsert(list);
        }

    }
}
