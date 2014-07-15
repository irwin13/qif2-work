package id.co.quadras.qif.core;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.irwin13.winwork.basic.WinWorkConstants;
import com.irwin13.winwork.basic.utilities.StringCompressor;
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
import id.co.quadras.qif.core.model.vo.message.QifMessageType;
import org.apache.commons.codec.binary.Base64;
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

    protected abstract QifActivityMessage receiveEvent(QifEvent qifEvent, Object inputMessage, QifMessageType messageType);
    protected abstract QifActivityResult implementProcess(QifActivityMessage qifActivityMessage) throws Exception;

    public QifActivityResult executeProcess(QifEvent qifEvent, Object inputMessage, QifMessageType messageType, QifActivityLog parentProcessLog) throws Exception {
        QifActivityResult qifActivityResult;
        if (qifEvent.getActiveAcceptMessage() != null && qifEvent.getActiveAcceptMessage()) {
            QifActivityMessage qifActivityMessage = receiveEvent(qifEvent, inputMessage, QifMessageType.TEXT);
            if (qifActivityMessage != null) {
                qifEventLog = insertEventLog(qifEvent, inputMessage, messageType);
                processLog = insertProcessLog(qifEvent, qifActivityMessage, parentProcessLog);
                qifActivityResult = implementProcess(qifActivityMessage);
                updateProcessLog(qifActivityMessage, qifActivityResult);
            } else {
                qifActivityResult = new QifActivityResult(SUCCESS, null, QifMessageType.TEXT, null);
            }
        } else {
            qifActivityResult = new QifActivityResult(SUCCESS, NOT_ACTIVE, QifMessageType.TEXT, null);
        }
        return qifActivityResult;
    }

    private QifActivityLog insertProcessLog(QifEvent qifEvent, QifActivityMessage qifActivityMessage,
                                            QifActivityLog parentProcessLog) {

        boolean auditTrailEnabled = qifEvent.getAuditTrailEnabled();

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

            boolean keepMessageContent = qifEvent.getKeepMessageContent();

            if (keepMessageContent) {
                if (qifActivityMessage != null) {
                    QifActivityLogInputMsg inputMsg = new QifActivityLogInputMsg();
                    inputMsg.setId(StringUtil.random32UUID());
                    inputMsg.setActivityLogId(id);
                    inputMsg.setMsgType(qifActivityMessage.getMessageType().getName());
                    inputMsg.setActive(Boolean.TRUE);
                    inputMsg.setCreateBy(activityName());
                    inputMsg.setLastUpdateBy(activityName());
                    inputMsg.setCreateDate(new Date());
                    inputMsg.setLastUpdateDate(new Date());

                    if (QifMessageType.TEXT.equals(qifActivityMessage.getMessageType())) {
                        inputMsg.setInputMessageContent(StringCompressor.compress(new String(qifActivityMessage.getContent())));
                    } else if (QifMessageType.BINARY.equals(qifActivityMessage.getMessageType())) {
                        inputMsg.setInputMessageContent(
                                Base64.encodeBase64String(qifActivityMessage.getContent()));
                    }
                    inputMessageQueue.put(inputMsg);
                }
            } else {
                logger.debug("Process {} keepMessageContent disabled", getClass().getName());
            }
        } else {
            logger.debug("Process {} auditTrailEnabled disabled", getClass().getName());
        }

        addCounterProcess();

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
                    for (Map.Entry<String, Object> entry : qifActivityResult.getAdditionalData().entrySet()) {
                        QifActivityLogData logData = new QifActivityLogData();
                        logData.setDataKey(entry.getKey());
                        Object value = entry.getValue();
                        logData.setDataValue((value != null) ? value.toString() : null);
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
                    outputMessage.setMsgType(qifActivityMessage.getMessageType().getName());

                    outputMessage.setActive(Boolean.TRUE);
                    outputMessage.setCreateBy(activityName());
                    outputMessage.setLastUpdateBy(activityName());
                    outputMessage.setCreateDate(new Date());
                    outputMessage.setLastUpdateDate(new Date());
                    if (QifMessageType.TEXT.equals(qifActivityResult.getMessageType())) {
                        String content;
                        if (qifActivityResult.getResult() instanceof String) {
                            content = (String) qifActivityResult.getResult();
                        } else {
                            try {
                                content = jsonParser.parseToString(false, qifActivityResult.getResult());
                            } catch (IOException e) {
                                logger.error(e.getLocalizedMessage(), e);
                                content = ExceptionUtils.getStackTrace(e);
                            }
                        }

                        outputMessage.setOutputMessageContent(StringCompressor.compress(content));
                    } else if (QifMessageType.BINARY.equals(qifActivityResult.getMessageType())) {
                        outputMessage.setOutputMessageContent(
                                Base64.encodeBase64String((byte[]) qifActivityResult.getResult()));
                    }
                    outputMessageQueue.put(outputMessage);
                }
            } else {
                logger.debug("Process {} keepMessageContent disabled", getClass().getName());
            }
        }
    }

    private QifEventLog insertEventLog(QifEvent qifEvent, Object inputMessage, QifMessageType messageType) {

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

            if (qifEvent.getKeepMessageContent() != null && qifEvent.getKeepMessageContent()
                    && inputMessage != null) {
                QifEventLogMsg logContent = new QifEventLogMsg();
                logContent.setId(StringUtil.random32UUID());
                logContent.setMsgType(messageType.getName());

                logContent.setEventLogId(generatedId);

                if (QifMessageType.TEXT.equals(messageType)) {
                    String content;
                    if (inputMessage instanceof String) {
                        content = (String) inputMessage;
                    } else {
                        try {
                            content = jsonParser.parseToString(false, inputMessage);
                        } catch (IOException e) {
                            logger.error(e.getLocalizedMessage(), e);
                            content = ExceptionUtils.getStackTrace(e);
                        }
                    }
                    logContent.setMessageContent(StringCompressor.compress(content));
                } else if (QifMessageType.BINARY.equals(messageType)) {
                    logContent.setMessageContent(
                            Base64.encodeBase64String((byte[]) inputMessage));
                }

                logContent.setActive(Boolean.TRUE);
                logContent.setCreateBy(activityName());
                logContent.setLastUpdateBy(activityName());
                logContent.setCreateDate(today);
                logContent.setLastUpdateDate(today);

                messageQueue.put(logContent);
            } else {
                logger.debug("Process {} keepMessageContent disabled", getClass().getName());
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
                                            QifActivityMessage qifActivityMessage) throws Exception{
        QifTask qifTask = injector.getInstance(taskClass);
        return qifTask.executeTask(this, qifActivityMessage);
    }

    protected QifActivityResult executeTaskAsynchronous(Injector injector, Class<? extends QifTask> taskClass,
                                                        QifActivityMessage qifActivityMessage) throws Exception {
        // TODO use ExecutorService and Future here
        QifTask qifTask = injector.getInstance(taskClass);
        return qifTask.executeTask(this, qifActivityMessage);
    }

    protected void executeTaskParallel(Injector injector, Class<? extends QifTask> taskClass,
                                       QifActivityMessage qifActivityMessage) throws Exception {
        // TODO use ThreadRunnable here
        QifTask qifTask = injector.getInstance(taskClass);
        qifTask.executeTask(this, qifActivityMessage);
    }

}
