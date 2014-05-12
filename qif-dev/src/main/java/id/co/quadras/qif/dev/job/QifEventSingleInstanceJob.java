package id.co.quadras.qif.dev.job;

import com.irwin13.winwork.basic.exception.WinWorkException;
import id.co.quadras.qif.QifConstants;
import id.co.quadras.qif.QifEventReceiver;
import id.co.quadras.qif.dev.guice.GuiceFactory;
import id.co.quadras.qif.dev.service.EventService;
import id.co.quadras.qif.model.entity.QifEvent;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author irwin Timestamp : 12/05/2014 18:27
 */
@DisallowConcurrentExecution
public class QifEventSingleInstanceJob implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(QifEventSingleInstanceJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String id = (String) context.getMergedJobDataMap().get(QifConstants.QIF_EVENT_ID);
        EventService eventService = GuiceFactory.getInjector().getInstance(EventService.class);
        QifEvent qifEvent = eventService.selectById(id);
        if (qifEvent != null) {
            if (qifEvent.getActiveAcceptMessage() != null && qifEvent.getActiveAcceptMessage()) {
                try {
                    QifEventReceiver eventReceiver = (QifEventReceiver) GuiceFactory.getInjector()
                            .getInstance(Class.forName(qifEvent.getQifReceiver()));
                    eventReceiver.receiveMessage(qifEvent);
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
