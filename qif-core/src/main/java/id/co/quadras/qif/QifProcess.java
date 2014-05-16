package id.co.quadras.qif;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.irwin13.winwork.basic.utilities.StringUtil;
import com.irwin13.winwork.basic.utilities.WinWorkUtil;
import id.co.quadras.qif.helper.JsonParser;
import id.co.quadras.qif.helper.queue.*;
import id.co.quadras.qif.model.entity.QifEvent;
import id.co.quadras.qif.model.entity.log.*;
import id.co.quadras.qif.model.vo.QifActivityResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author irwin Timestamp : 07/04/2014 11:53
 */
public abstract class QifProcess implements QifActivity {

    public static final String TYPE = "process";
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String activityType() {
        return TYPE;
    }

    @Inject
    protected JsonParser jsonParser;

    @Inject
    private ActivityLogQueue activityLogQueue;

    @Inject
    private ActivityLogUpdateQueue activityLogUpdateQueue;

    @Inject
    private ActivityLogInputMessageQueue inputMessageQueue;

    @Inject
    private ActivityLogOutputMessageQueue outputMessageQueue;

    @Inject
    private EventLogQueue eventLogQueue;

    @Inject
    private EventLogMessageQueue messageQueue;

    private QifActivityLog processLog;

    protected QifActivityLog getProcessActivityLog() {
        return processLog;
    }

    protected abstract QifActivityMessage receiveEvent(QifEvent qifEvent);
    protected abstract QifActivityResult implementProcess(QifActivityMessage qifActivityMessage);

    public QifActivityResult executeProcess(QifEvent qifEvent) {
        QifActivityMessage qifActivityMessage = receiveEvent(qifEvent);
        processLog = insertProcessLog(qifActivityMessage);
        QifActivityResult qifActivityResult = implementProcess(qifActivityMessage);
        updateProcessLog(qifActivityMessage, qifActivityResult);
        return qifActivityResult;
    }

    private QifActivityLog insertProcessLog(QifActivityMessage qifActivityMessage) {

        boolean auditTrailEnabled = qifActivityMessage.getQifEventLog().getQifEvent().getAuditTrailEnabled();

        if (auditTrailEnabled) {
            String id = StringUtil.random32UUID();
            processLog = new QifActivityLog();

            processLog.setId(id);
            processLog.setEventLogId(qifActivityMessage.getQifEventLog().getId());
            processLog.setActivityType(activityType());
            processLog.setNodeName(WinWorkUtil.getNodeName());
            processLog.setStartTime(System.currentTimeMillis());

            processLog.setCreateBy(activityName());
            processLog.setLastUpdateBy(activityName());

            activityLogQueue.put(processLog);

            boolean keepMessageContent = qifActivityMessage.getQifEventLog().getQifEvent().getKeepMessageContent();

            if (keepMessageContent) {
                if (qifActivityMessage.getMessage() != null) {
                    QifActivityLogInputMessage inputMessage = new QifActivityLogInputMessage();
                    inputMessage.setId(StringUtil.random32UUID());
                    inputMessage.setActivityLogId(id);
                    inputMessage.setCreateBy(activityName());
                    inputMessage.setLastUpdateBy(activityName());
                    inputMessage.setCreateDate(new Date());
                    inputMessage.setLastUpdateDate(new Date());
                    try {
                        inputMessage.setInputMessageContent(jsonParser.parseToString(
                                true, qifActivityMessage.getMessage()));
                    } catch (IOException e) {
                        logger.error(e.getLocalizedMessage(), e);
                        inputMessage.setInputMessageContent(e.getMessage());
                    }
                    inputMessageQueue.put(inputMessage);
                }
            }
        }

        return processLog;
    }

    private void updateProcessLog(QifActivityMessage qifActivityMessage, QifActivityResult qifActivityResult) {

        if (processLog != null) {

            String activityStatus = null;
            List<QifActivityLogData> activityLogDataList = null;

            if (qifActivityResult != null) {
                activityStatus = qifActivityResult.getStatus();
                if (qifActivityResult.getAdditionalData() != null) {
                    activityLogDataList = new LinkedList<QifActivityLogData>();
                    for (Map.Entry<String, String> entry : qifActivityResult.getAdditionalData().entrySet()) {
                        QifActivityLogData logData = new QifActivityLogData();
                        logData.setDataKey(entry.getKey());
                        logData.setDataValue(entry.getValue());
                        logData.setActivityLogId(processLog.getId());
                        activityLogDataList.add(logData);
                    }
                }
            }

            processLog.setActivityStatus(activityStatus);
            processLog.setExecutionTime(System.currentTimeMillis() - processLog.getStartTime());
            processLog.setQifActivityLogDataList(activityLogDataList);

            processLog.setLastUpdateBy(activityName());
            processLog.setLastUpdateDate(new Date());

            activityLogUpdateQueue.put(processLog);

            boolean keepMessageContent = qifActivityMessage.getQifEventLog().getQifEvent().getKeepMessageContent();

            if (keepMessageContent) {
                if (qifActivityResult != null && qifActivityResult.getResult() != null) {
                    QifActivityLogOutputMessage outputMessage = new QifActivityLogOutputMessage();
                    outputMessage.setId(StringUtil.random32UUID());
                    outputMessage.setActivityLogId(processLog.getId());
                    outputMessage.setCreateBy(activityName());
                    outputMessage.setLastUpdateBy(activityName());
                    outputMessage.setCreateDate(new Date());
                    outputMessage.setLastUpdateDate(new Date());
                    try {
                        outputMessage.setOutputMessageContent(jsonParser
                                .parseToString(true, qifActivityResult.getResult()));
                    } catch (IOException e) {
                        logger.error(e.getLocalizedMessage(), e);
                        outputMessage.setOutputMessageContent(e.getMessage());
                    }
                    outputMessageQueue.put(outputMessage);
                }
            }
        }
    }

    protected QifEventLog insertEventLog(QifEvent qifEvent) {

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
                try {
                    logContent.setMessageContent(jsonParser.parseToString(true, qifEvent.getEventMessage()));
                } catch (IOException e) {
                    logger.error(e.getLocalizedMessage(), e);
                }

                logContent.setCreateBy(activityName());
                logContent.setLastUpdateBy(activityName());
                logContent.setCreateDate(today);
                logContent.setLastUpdateDate(today);

                messageQueue.put(logContent);
            }

        } else {
            logger.debug("auditTrail is not enabled for QifEvent {}", qifEvent.getName());
            if (!Strings.isNullOrEmpty(qifEvent.getId())) {
                qifEventLog.setQifEvent(qifEvent);
            }
            qifEventLog.setNodeName(WinWorkUtil.getNodeName());
        }

        return qifEventLog;
    }

}
