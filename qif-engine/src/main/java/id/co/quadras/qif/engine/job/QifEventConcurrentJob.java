package id.co.quadras.qif.engine.job;

import com.irwin13.winwork.basic.exception.WinWorkException;
import id.co.quadras.qif.core.QifConstants;
import id.co.quadras.qif.core.QifProcess;
import id.co.quadras.qif.core.model.entity.QifEvent;
import id.co.quadras.qif.core.model.vo.message.QifMessageType;
import id.co.quadras.qif.engine.guice.EngineFactory;
import id.co.quadras.qif.engine.service.EventService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author irwin Timestamp : 12/05/2014 18:29
 */
public class QifEventConcurrentJob implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(QifEventSingleInstanceJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String id = (String) context.getMergedJobDataMap().get(QifConstants.QIF_EVENT_ID);
        EventService eventService = EngineFactory.getInjector().getInstance(EventService.class);
        QifEvent qifEvent = eventService.selectById(id);
        if (qifEvent != null) {
            if (qifEvent.getActiveAcceptMessage() != null && qifEvent.getActiveAcceptMessage()) {
                try {
                    QifProcess qifProcess = (QifProcess) EngineFactory.getInjector()
                            .getInstance(Class.forName(qifEvent.getQifProcess()));
                    qifProcess.executeEvent(qifEvent, null, QifMessageType.STRING);
                } catch (Exception e) {
                    LOGGER.error(e.getLocalizedMessage(), e);
                }
            } else {
                LOGGER.info("QifEvent {} is NOT Active Accept Message", qifEvent.getName());
            }
        } else {
            throw new WinWorkException("FATAL : Unexpected error, missing QifEvent in database with id = "
                    + id);
        }
    }
}
