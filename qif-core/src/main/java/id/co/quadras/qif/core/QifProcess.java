package id.co.quadras.qif.core;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.irwin13.winwork.basic.WinWorkConstants;
import com.irwin13.winwork.basic.utilities.StringUtil;
import com.irwin13.winwork.basic.utilities.WinWorkUtil;
import id.co.quadras.qif.core.helper.JsonParser;
import id.co.quadras.qif.core.helper.JsonPrettyPrint;
import id.co.quadras.qif.core.helper.QifTransactionCounter;
import id.co.quadras.qif.core.helper.queue.*;
import id.co.quadras.qif.core.model.entity.QifEvent;
import id.co.quadras.qif.core.model.entity.QifEventProperty;
import id.co.quadras.qif.core.model.entity.log.*;
import id.co.quadras.qif.core.model.vo.QifActivityResult;
import id.co.quadras.qif.core.model.vo.message.QifMessageType;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

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
    protected ExecutorService threadPool;
    
    @Inject
    protected JsonParser jsonParser;

    @Inject
    protected JsonPrettyPrint jsonPrettyPrint;

    @Inject
    protected QifTransactionCounter transactionCounter;

    @Inject
    private ActivityLogQueue activityLogQueue;

    @Inject
    private ActivityLogUpdateQueue activityLogUpdateQueue;

    @Inject
    private ActivityLogDataQueue activityLogDataQueue;

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

    protected abstract QifActivityResult handleEvent(QifEvent qifEvent, Object eventMessage,
                                                      QifMessageType messageType) throws Exception;

    protected abstract QifActivityResult implementProcess(QifActivityMessage qifActivityMessage) throws Exception;

    public QifActivityResult executeEvent(QifEvent qifEvent, Object eventMessage,
                                          QifMessageType messageType) throws Exception {

        boolean activeAcceptMessage = qifEvent.getActiveAcceptMessage();
        QifActivityResult qifActivityResult;

        if (activeAcceptMessage) {
            qifEventLog = insertEventLog(qifEvent, eventMessage, messageType);
            qifActivityResult = handleEvent(qifEvent, eventMessage, messageType);
        } else {
            qifActivityResult = new QifActivityResult(SUCCESS, NOT_ACTIVE, QifMessageType.STRING);
        }

        return qifActivityResult;
    }

    protected QifActivityResult executeProcess(QifEvent qifEvent, QifActivityMessage qifActivityMessage) throws Exception {
        if (qifEventLog == null) {
            insertEventLog(qifEvent, qifActivityMessage.getMessageContent(), qifActivityMessage.getMessageType());
        }
        processLog = insertProcessLog(qifEvent, qifActivityMessage);
        QifActivityResult qifActivityResult = implementProcess(qifActivityMessage);
        updateProcessLog(qifActivityMessage, qifActivityResult);
        return qifActivityResult;
    }

    public abstract Runnable createDaemon(QifEvent qifEvent) throws Exception;

    private QifActivityLog insertProcessLog(QifEvent qifEvent, QifActivityMessage qifActivityMessage) {

        addCounterProcess();
        boolean auditTrailEnabled = qifEvent.getAuditTrailEnabled();

        if (auditTrailEnabled) {
            Date now = new Date();

            String id = StringUtil.random32UUID();
            processLog = new QifActivityLog();

            processLog.setId(id);
            processLog.setEventLogId(qifEventLog.getId());
            processLog.setActivityType(activityType());
            processLog.setNodeName(WinWorkUtil.getNodeName());
            processLog.setStartTime(System.currentTimeMillis());

            processLog.setActive(Boolean.TRUE);
            processLog.setCreateDate(now);
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
                    inputMsg.setCreateDate(now);
                    inputMsg.setLastUpdateDate(now);
                    inputMsg.setInputMessageContent(QifUtil
                            .convertObjectContentToString(
                                    qifActivityMessage.getMessageContent(), qifActivityMessage.getMessageType(),
                                    jsonPrettyPrint.getObjectMapper()));
                    inputMessageQueue.put(inputMsg);
                }
            } else {
                logger.debug("Process {} keepMessageContent disabled", getClass().getName());
            }
        } else {
            logger.debug("Process {} auditTrailEnabled disabled", getClass().getName());
        }

        return processLog;
    }

    private void updateProcessLog(QifActivityMessage qifActivityMessage, QifActivityResult qifActivityResult) {

        if (processLog != null) {

            Date now = new Date();
            String activityStatus = null;
            List<QifActivityLogData> activityLogDataList = null;

            if (qifActivityResult != null) {
                activityStatus = qifActivityResult.getStatus();
                if (qifActivityResult.getActivityData() != null) {
                    activityLogDataList = new LinkedList<QifActivityLogData>();
                    for (Map.Entry<String, Object> entry : qifActivityResult.getActivityData().entrySet()) {
                        QifActivityLogData logData = new QifActivityLogData();

                        logData.setId(StringUtil.random32UUID());
                        logData.setCreateBy(activityName());
                        logData.setLastUpdateBy(activityName());
                        logData.setCreateDate(now);
                        logData.setLastUpdateDate(now);
                        logData.setActive(Boolean.TRUE);

                        logData.setDataKey(entry.getKey());
                        Object value = entry.getValue();
                        logData.setDataValue((value != null) ? value.toString() : null);
                        logData.setActivityLogId(processLog.getId());

                        activityLogDataList.add(logData);
                        activityLogDataQueue.put(logData);
                    }
                }
            }

            processLog.setActivityStatus(activityStatus);
            processLog.setExecutionTime(now.getTime() - processLog.getStartTime());
            processLog.setQifActivityLogDataList(activityLogDataList);

            processLog.setLastUpdateBy(activityName());
            processLog.setLastUpdateDate(now);

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
                    outputMessage.setCreateDate(now);
                    outputMessage.setLastUpdateDate(now);
                    outputMessage.setOutputMessageContent(QifUtil
                            .convertObjectContentToString(
                                    qifActivityResult.getResult(), qifActivityResult.getMessageType(),
                                    jsonPrettyPrint.getObjectMapper()));
                    outputMessageQueue.put(outputMessage);
                }
            } else {
                logger.debug("Process {} keepMessageContent disabled", getClass().getName());
            }
        }
    }

    private QifEventLog insertEventLog(QifEvent qifEvent, Object eventMessage, QifMessageType messageType) {

        addCounterEvent(qifEvent);
        QifEventLog qifEventLog = new QifEventLog();

        if (qifEvent.getAuditTrailEnabled() != null && qifEvent.getAuditTrailEnabled()) {

            String generatedId = StringUtil.random32UUID();
            Date now = new Date();

            qifEventLog.setId(generatedId);
            qifEventLog.setEventId(qifEvent.getId());
            qifEventLog.setNodeName(WinWorkUtil.getNodeName());
            qifEventLog.setQifEvent(qifEvent);

            qifEventLog.setActive(Boolean.TRUE);
            qifEventLog.setCreateBy(activityName());
            qifEventLog.setLastUpdateBy(activityName());
            qifEventLog.setCreateDate(now);
            qifEventLog.setLastUpdateDate(now);

            eventLogQueue.put(qifEventLog);

            if (qifEvent.getKeepMessageContent() != null && qifEvent.getKeepMessageContent()
                    && eventMessage != null) {
                QifEventLogMsg logContent = new QifEventLogMsg();

                logContent.setId(StringUtil.random32UUID());
                logContent.setMsgType(messageType.getName());
                logContent.setEventLogId(generatedId);

                logContent.setActive(Boolean.TRUE);
                logContent.setCreateBy(activityName());
                logContent.setLastUpdateBy(activityName());
                logContent.setCreateDate(now);
                logContent.setLastUpdateDate(now);
                logContent.setMessageContent(QifUtil
                        .convertObjectContentToString(eventMessage, messageType, jsonPrettyPrint.getObjectMapper()));
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

        return qifEventLog;
    }

    private void addCounterEvent(QifEvent qifEvent) {
        transactionCounter.add(qifEvent.getName());
        transactionCounter.add(qifEvent.getName() + "_" + WinWorkConstants.SDF_DEFAULT.format(new Date()));
    }

    private void addCounterProcess() {
        transactionCounter.add(this.getClass().getName());
        transactionCounter.add(this.getClass().getName() + "_" + WinWorkConstants.SDF_DEFAULT.format(new Date()));
    }

    protected String getPropertyValue(QifEvent qifEvent, String propertyKey) {
        QifEventProperty property = QifUtil.getEventProperty(qifEvent, propertyKey);
        return (property == null) ? null : property.getPropertyValue();
    }

    protected QifActivityResult executeTask(Injector injector, Class<? extends QifTask> taskClass,
                                            QifActivityMessage qifActivityMessage) throws Exception{
        QifTask qifTask = injector.getInstance(taskClass);
        return qifTask.executeTask(this, qifActivityMessage);
    }

    protected QifActivityResult executeTaskWithFuture(Injector injector, Class<? extends QifTask> taskClass,
                                                      final QifActivityMessage qifActivityMessage,
                                                      long timeout, TimeUnit timeUnit) throws Exception {
    	    	
        final QifTask qifTask = injector.getInstance(taskClass);
        final QifProcess qifProcess = this;
        Future<QifActivityResult> future = threadPool.submit(new Callable<QifActivityResult>() {
			@Override
			public QifActivityResult call() throws Exception {
				return qifTask.executeTask(qifProcess, qifActivityMessage);
			}
        	
		});

        QifActivityResult result;
        if (timeout <= 0 || timeUnit == null) {
        	result = future.get();
        } else {
            try {
                result = future.get(timeout, timeUnit);
            } catch (InterruptedException e) {
                logger.error(e.getLocalizedMessage(), e);
                future.cancel(true);
                result = new QifActivityResult(ERROR, ExceptionUtils.getStackTrace(e), QifMessageType.STRING);
            } catch (ExecutionException e) {
                logger.error(e.getLocalizedMessage(), e);
                future.cancel(true);
                result = new QifActivityResult(ERROR, ExceptionUtils.getStackTrace(e), QifMessageType.STRING);
            } catch (TimeoutException e) {
                logger.error(e.getLocalizedMessage(), e);
                future.cancel(true);
                result = new QifActivityResult(ERROR, ExceptionUtils.getStackTrace(e), QifMessageType.STRING);
            }
        }

        return result;
    }

    protected void executeTaskWithThread(Injector injector, Class<? extends QifTask> taskClass,
                                         final QifActivityMessage qifActivityMessage) throws Exception {
        
        final QifTask qifTask = injector.getInstance(taskClass);
        final QifProcess qifProcess = this; 
        threadPool.submit(new Runnable() {			
			@Override
			public void run() {
				try {
					qifTask.executeTask(qifProcess, qifActivityMessage);
				} catch (Exception e) {
					logger.error(e.getLocalizedMessage(), e);
				}
			}
		});
    }

}
