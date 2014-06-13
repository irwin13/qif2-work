package id.co.quadras.qif.core;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.irwin13.winwork.basic.WinWorkConstants;
import com.irwin13.winwork.basic.utilities.StringUtil;
import com.irwin13.winwork.basic.utilities.WinWorkUtil;
import id.co.quadras.qif.core.exception.QifException;
import id.co.quadras.qif.core.helper.JsonParser;
import id.co.quadras.qif.core.helper.QifTransactionCounter;
import id.co.quadras.qif.core.helper.queue.*;
import id.co.quadras.qif.core.model.entity.QifEvent;
import id.co.quadras.qif.core.model.entity.QifEventProperty;
import id.co.quadras.qif.core.model.entity.log.*;
import id.co.quadras.qif.core.model.vo.QifActivityResult;
import org.apache.commons.lang3.exception.ExceptionUtils;
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

    public static final String NOT_ACTIVE = "not_active";
    public static final String TYPE = "process";
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String activityType() {
        return TYPE;
    }

    @Override
    public String activityName() {
        return getClass().getName();
    }

    @Inject
    protected JsonParser jsonParser;

    @Inject
    protected QifTransactionCounter transactionCounter;

    @Inject
    private ActivityLogQueue activityLogQueue;

    @Inject
    private ActivityLogUpdateQueue activityLogUpdateQueue;

    @Inject
    private ActivityLogInputMsgQueue inputMessageQueue;

    @Inject
    private ActivityLogOutputMsgQueue outputMessageQueue;

    @Inject
    private EventLogQueue eventLogQueue;

    @Inject
    private EventLogMsgQueue messageQueue;

    private QifActivityLog processLog;
    private QifEventLog qifEventLog;

    protected QifActivityLog getProcessActivityLog() {
        return processLog;
    }
    protected QifEventLog getQifEventLog() { return qifEventLog; }

    protected abstract Object receiveEvent(QifEvent qifEvent, Object inputMessage);
    protected abstract QifActivityResult implementProcess(Object processInput);

    public QifActivityResult executeProcess(QifEvent qifEvent, Object inputMessage, QifActivityLog parentProcessLog) {
        QifActivityResult qifActivityResult;
        if (qifEvent.getActiveAcceptMessage() != null && qifEvent.getActiveAcceptMessage()) {
            Object processInput = receiveEvent(qifEvent, inputMessage);
            qifEventLog = insertEventLog(qifEvent, inputMessage);
            if (processInput != null) {
                processLog = insertProcessLog(qifEventLog, inputMessage, parentProcessLog);
                qifActivityResult = implementProcess(processInput);
                updateProcessLog(qifEventLog, qifActivityResult);
            } else {
                qifActivityResult = new QifActivityResult(SUCCESS, null, null);
            }
        } else {
            qifActivityResult = new QifActivityResult(SUCCESS, NOT_ACTIVE, null);
        }
        return qifActivityResult;
    }

    private QifActivityLog insertProcessLog(QifEventLog qifEventLog, Object inputMessage,
                                            QifActivityLog parentProcessLog) {

        boolean auditTrailEnabled = qifEventLog.getQifEvent().getAuditTrailEnabled();

        if (auditTrailEnabled) {
            String id = StringUtil.random32UUID();
            processLog = new QifActivityLog();

            processLog.setId(id);
            processLog.setEventLogId(qifEventLog.getId());
            processLog.setActivityType(activityType());
            processLog.setNodeName(WinWorkUtil.getNodeName());
            processLog.setStartTime(System.currentTimeMillis());

            if (parentProcessLog != null) {
                processLog.setParentActivityId(parentProcessLog.getId());
                processLog.setParentActivity(parentProcessLog);
            }

            processLog.setActive(Boolean.TRUE);
            processLog.setCreateDate(new Date());
            processLog.setCreateBy(activityName());

            activityLogQueue.put(processLog);

            boolean keepMessageContent = qifEventLog.getQifEvent().getKeepMessageContent();

            if (keepMessageContent) {
                if (inputMessage != null) {
                    QifActivityLogInputMsg inputMsg = new QifActivityLogInputMsg();
                    inputMsg.setId(StringUtil.random32UUID());
                    inputMsg.setActivityLogId(id);
                    inputMsg.setActive(Boolean.TRUE);
                    inputMsg.setCreateBy(activityName());
                    inputMsg.setLastUpdateBy(activityName());
                    inputMsg.setCreateDate(new Date());
                    inputMsg.setLastUpdateDate(new Date());
                    try {
                        inputMsg.setInputMessageContent(jsonParser.parseToString(
                                true, inputMessage));
                    } catch (IOException e) {
                        logger.error(e.getLocalizedMessage(), e);
                        inputMsg.setInputMessageContent(ExceptionUtils.getStackTrace(e.getCause()));
                    }
                    inputMessageQueue.put(inputMsg);
                }
            }
        }

        addCounterProcess();

        return processLog;
    }

    private void updateProcessLog(QifEventLog qifEventLog, QifActivityResult qifActivityResult) {

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

            boolean keepMessageContent = qifEventLog.getQifEvent().getKeepMessageContent();

            if (keepMessageContent) {
                if (qifActivityResult != null && qifActivityResult.getResult() != null) {
                    QifActivityLogOutputMsg outputMessage = new QifActivityLogOutputMsg();
                    outputMessage.setId(StringUtil.random32UUID());
                    outputMessage.setActivityLogId(processLog.getId());
                    outputMessage.setActive(Boolean.TRUE);
                    outputMessage.setCreateBy(activityName());
                    outputMessage.setLastUpdateBy(activityName());
                    outputMessage.setCreateDate(new Date());
                    outputMessage.setLastUpdateDate(new Date());
                    try {
                        outputMessage.setOutputMessageContent(jsonParser
                                .parseToString(true, qifActivityResult.getResult()));
                    } catch (IOException e) {
                        logger.error(e.getLocalizedMessage(), e);
                        outputMessage.setOutputMessageContent(ExceptionUtils.getStackTrace(e.getCause()));
                    }
                    outputMessageQueue.put(outputMessage);
                }
            }
        }
    }

    private QifEventLog insertEventLog(QifEvent qifEvent, Object inputMessage) {

        QifEventLog qifEventLog = new QifEventLog();

        if (qifEvent.getAuditTrailEnabled() != null && qifEvent.getAuditTrailEnabled()) {

            String generatedId = StringUtil.random32UUID();
            Date today = new Date();

            qifEventLog.setId(generatedId);
            qifEventLog.setEventId(qifEvent.getId());
            qifEventLog.setNodeName(WinWorkUtil.getNodeName());
            qifEventLog.setQifEvent(qifEvent);

            qifEventLog.setActive(Boolean.TRUE);
            qifEventLog.setCreateBy(activityName());
            qifEventLog.setLastUpdateBy(activityName());
            qifEventLog.setCreateDate(today);
            qifEventLog.setLastUpdateDate(today);

            eventLogQueue.put(qifEventLog);

            if (qifEvent.getKeepMessageContent() != null && qifEvent.getKeepMessageContent()) {
                QifEventLogMsg logContent = new QifEventLogMsg();
                logContent.setId(StringUtil.random32UUID());

                logContent.setEventLogId(generatedId);
                try {
                    logContent.setMessageContent(jsonParser.parseToString(true, inputMessage));
                } catch (IOException e) {
                    logger.error(e.getLocalizedMessage(), e);
                    logContent.setMessageContent(ExceptionUtils.getStackTrace(e.getCause()));
                }

                logContent.setActive(Boolean.TRUE);
                logContent.setCreateBy(activityName());
                logContent.setLastUpdateBy(activityName());
                logContent.setCreateDate(today);
                logContent.setLastUpdateDate(today);

                messageQueue.put(logContent);
            }

        } else {
            logger.debug("auditTrail is not enabled for QifEvent {}", qifEvent.getName());
            qifEventLog.setEventId(qifEvent.getId());
            qifEventLog.setQifEvent(qifEvent);
            qifEventLog.setNodeName(WinWorkUtil.getNodeName());
        }

        addCounterEvent(qifEvent);

        return qifEventLog;
    }

    protected String getPropertyValue(QifEvent qifEvent, String propertyKey) {
        List<QifEventProperty> list = qifEvent.getQifEventPropertyList();
        String result = null;
        if (list == null && list.isEmpty()) {
            throw new QifException("FATAL : empty property list on QifEvent " + qifEvent.getName());
        } else {
            for (QifEventProperty property : qifEvent.getQifEventPropertyList()) {
                if (property.getPropertyKey().equals(propertyKey)) {
                    result = property.getPropertyValue();
                }
            }
        }
        return result;
    }

    private void addCounterEvent(QifEvent qifEvent) {
        transactionCounter.add(qifEvent.getName());
        transactionCounter.add(qifEvent.getName() + "_" + WinWorkConstants.SDF_DEFAULT.format(new Date()));
    }

    private void addCounterProcess() {
        transactionCounter.add(this.getClass().getName());
        transactionCounter.add(this.getClass().getName() + "_" + WinWorkConstants.SDF_DEFAULT.format(new Date()));
    }

    protected QifActivityResult executeTask(Injector injector, Class<? extends QifTask> taskClass,
                                            Object taskInput) {
        QifTask qifTask = injector.getInstance(taskClass);
        return qifTask.executeTask(new QifTaskMessage(this, taskInput));
    }

    protected QifActivityResult executeTaskAsynchronous(Injector injector, Class<? extends QifTask> taskClass,
                                            Object taskInput) {
        // TODO use ExecutorService and Future here
        QifTask qifTask = injector.getInstance(taskClass);
        return qifTask.executeTask(new QifTaskMessage(this, taskInput));
    }

    protected void executeTaskParallel(Injector injector, Class<? extends QifTask> taskClass,
                                                       Object taskInput) {
        // TODO use ThreadRunnable here
        QifTask qifTask = injector.getInstance(taskClass);
        qifTask.executeTask(new QifTaskMessage(this, taskInput));
    }

}
