package id.co.quadras.qif.engine.job.internal;

import id.co.quadras.qif.core.QifConstants;
import id.co.quadras.qif.core.helper.queue.reader.EventLogQueueReader;
import id.co.quadras.qif.core.model.entity.log.QifEventLog;
import id.co.quadras.qif.engine.guice.EngineFactory;
import id.co.quadras.qif.engine.service.app.AppSettingService;
import id.co.quadras.qif.engine.service.log.EventLogService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author irwin Timestamp : 17/05/2014 20:57
 */
@DisallowConcurrentExecution
public class EventLogPersist implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventLogPersist.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        EventLogQueueReader queueReader = EngineFactory.getInjector().getInstance(EventLogQueueReader.class);
        EventLogService service = EngineFactory.getInjector().getInstance(EventLogService.class);

        AppSettingService appSettingService = EngineFactory.getInjector().getInstance(AppSettingService.class);
        int maxFetch = QifConstants.DEFAULT_LOG_FETCH;

        try {
            String max = appSettingService.selectByCode("queue.eventLog.maxFetch").getStringValue();
            maxFetch = Integer.valueOf(max);
        } catch (NumberFormatException e) {
            LOGGER.error(e.getLocalizedMessage());
        }

        List<QifEventLog> list = queueReader.getLogList(maxFetch);
        LOGGER.debug("maxFetch = {}", maxFetch);
        LOGGER.debug("list size = {}", list.size());

        if (!list.isEmpty()) {
            service.batchInsert(list);
        }
    }
}
