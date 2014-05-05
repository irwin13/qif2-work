package id.co.quadras.qif;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.irwin13.winwork.basic.utilities.StringUtil;
import com.irwin13.winwork.basic.utilities.WinWorkUtil;
import id.co.quadras.qif.helper.JsonParser;
import id.co.quadras.qif.helper.queue.EventLogMessageQueue;
import id.co.quadras.qif.helper.queue.EventLogQueue;
import id.co.quadras.qif.model.entity.QifEvent;
import id.co.quadras.qif.model.entity.log.QifEventLog;
import id.co.quadras.qif.model.entity.log.QifEventLogMessage;
import id.co.quadras.qif.model.vo.QifEventResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author irwin Timestamp : 07/04/2014 11:53
 */
public abstract class QifEventReceiver implements QifActivity {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    public static final String TYPE = "eventReceiver";

    @Override
    public String activityType() {
        return TYPE;
    }

    @Inject
    protected EventLogQueue eventLogQueue;

    @Inject
    protected EventLogMessageQueue messageQueue;

    @Inject
    protected JsonParser jsonParser;

    protected abstract QifEventResult implementReceiver(QifEventLog qifEventLog);

    /**
     * Typical execution will be like this :
     *
     * <code>
     * // run your Business Process here
     * QifEventResult result;
     * QifEventLog qifEventLog = insertEventLog(qifEvent);
     * BpMessage bpMessage = new BpMessage(bpReceiverLog, jsonMessage);
     * bpTask.executeTask(bpMessage); // execute the BpFlow
     * return bpActivityResult;
     * </code>
     *
     * @param qifEvent
     * @return
     */
    public QifEventResult receiveMessage(QifEvent qifEvent) {
        QifEventLog qifEventLog = insertEventLog(qifEvent);
        return implementReceiver(qifEventLog);
    }

    private QifEventLog insertEventLog(QifEvent qifEvent) {

        QifEventLog qifEventLog = new QifEventLog();

        if (qifEvent.getAuditTrailEnabled() != null && qifEvent.getAuditTrailEnabled()) {

            String generatedId = StringUtil.random32UUID();
            Date today = new Date();

            qifEventLog.setId(generatedId);
            if (!Strings.isNullOrEmpty(qifEvent.getId())) {
                qifEventLog.setEventId(qifEvent.getId());
            }
            qifEventLog.setNodeName(WinWorkUtil.getNodeName());

            qifEventLog.setCreateBy(activityName());
            qifEventLog.setLastUpdateBy(activityName());
            qifEventLog.setCreateDate(today);
            qifEventLog.setLastUpdateDate(today);

            eventLogQueue.put(qifEventLog);

            if (qifEvent.getKeepMessageContent() != null && qifEvent.getKeepMessageContent()) {
                QifEventLogMessage logContent = new QifEventLogMessage();
                logContent.setId(StringUtil.random32UUID());

                logContent.setEventLogId(generatedId);
                logContent.setMessageContent(qifEvent.getEventMessage());

                logContent.setCreateBy(activityName());
                logContent.setLastUpdateBy(activityName());
                logContent.setCreateDate(today);
                logContent.setLastUpdateDate(today);

                messageQueue.put(logContent);
            }

        } else {
            logger.debug("auditTrail is not enabled");
            if (!Strings.isNullOrEmpty(qifEvent.getId())) {
                qifEventLog.setQifEvent(qifEvent);
            }
            qifEventLog.setNodeName(WinWorkUtil.getNodeName());
        }

        return qifEventLog;
    }

}
