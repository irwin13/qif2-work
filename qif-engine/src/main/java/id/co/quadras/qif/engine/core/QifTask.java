package id.co.quadras.qif.engine.core;

import com.google.inject.Inject;
import com.irwin13.winwork.basic.WinWorkConstants;
import com.irwin13.winwork.basic.utilities.StringUtil;
import com.irwin13.winwork.basic.utilities.WinWorkUtil;
import id.co.quadras.qif.engine.counter.QifTransactionCounter;
import id.co.quadras.qif.engine.json.QifJsonParser;
import id.co.quadras.qif.engine.json.JsonPrettyPrint;
import id.co.quadras.qif.engine.queue.ActivityLogDataQueue;
import id.co.quadras.qif.engine.queue.ActivityLogInputMsgQueue;
import id.co.quadras.qif.engine.queue.ActivityLogOutputMsgQueue;
import id.co.quadras.qif.engine.queue.ActivityLogQueue;
import id.co.quadras.qif.model.entity.log.QifActivityLog;
import id.co.quadras.qif.model.entity.log.QifActivityLogData;
import id.co.quadras.qif.model.entity.log.QifActivityLogInputMsg;
import id.co.quadras.qif.model.entity.log.QifActivityLogOutputMsg;
import id.co.quadras.qif.model.vo.QifActivityResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author irwin Timestamp : 07/04/2014 11:53
 */
public abstract class QifTask implements QifActivity {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    public static final String TYPE = "task";

    @Override
    public String activityType() {
        return TYPE;
    }

    @Override
    public String activityName() {
        return getClass().getName();
    }

    protected abstract QifActivityResult implementTask(QifActivityMessage qifActivityMessage) throws Exception;

    @Inject
    protected QifJsonParser qifJsonParser;

    @Inject
    protected JsonPrettyPrint jsonPrettyPrint;

    /**
     * Typical execution will be like this :
     *
     * <code>
     * long start = System.currentTimeMillis();
     * // run your Business Process here
     * QifActivityResult result; // construct QifActivityResult
     * insertAuditTrail(qifActivityMessage, start, result); // insert log
     * return bpActivityResult;
     * </code>
     *
     *
     *
     * @param qifActivityMessage
     * @return
     */
    public QifActivityResult executeTask(QifProcess qifProcess, QifActivityMessage qifActivityMessage) throws Exception {
        long start = System.currentTimeMillis();
        QifActivityResult bpActivityResult = implementTask(qifActivityMessage);
        insertAuditTrail(qifProcess, qifActivityMessage, start, bpActivityResult);
        return bpActivityResult;
    }

    @Inject
    protected QifTransactionCounter transactionCounter;

    @Inject
    private ActivityLogQueue activityLogQueue;

    @Inject
    private ActivityLogDataQueue activityLogDataQueue;

    @Inject
    private ActivityLogInputMsgQueue inputMessageQueue;

    @Inject
    private ActivityLogOutputMsgQueue outputMessageQueue;

    private void insertAuditTrail(QifProcess qifProcess, QifActivityMessage qifActivityMessage, long start, QifActivityResult qifActivityResult) {

        addCounterTask();
        boolean auditTrailEnabled = qifProcess.getQifEventLog().getQifEvent().getAuditTrailEnabled();

        if (auditTrailEnabled) {
            String taskLogId = StringUtil.random32UUID();
            Date now = new Date();

            QifActivityLog taskLog = new QifActivityLog();
            String activityStatus = null;
            List<QifActivityLogData> activityLogDataList = null;

            if (qifActivityResult != null) {
                activityStatus = qifActivityResult.getStatus();
                if (qifActivityResult.getActivityData() != null) {
                    activityLogDataList = new LinkedList<QifActivityLogData>();
                    for (Map.Entry<String, Object> entry : qifActivityResult.getActivityData().entrySet()) {
                        logger.debug("log data key = {} with value = {}", entry.getKey(), entry.getValue());

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
                        logData.setActivityLogId(taskLogId);

                        activityLogDataList.add(logData);
                        activityLogDataQueue.put(logData);
                    }
                }

            }

            taskLog.setId(taskLogId);
            taskLog.setQifEventLog(qifProcess.getQifEventLog());
            taskLog.setActivityStatus(activityStatus);
            taskLog.setActivityType(activityType());
            taskLog.setExecutionTime(now.getTime() - start);
            taskLog.setNodeName(WinWorkUtil.getNodeName());
            taskLog.setStartTime(start);
            taskLog.setQifActivityLogDataList(activityLogDataList);
            taskLog.setParentActivity(qifProcess.getProcessActivityLog());
            taskLog.setParentActivityId(qifProcess.getProcessActivityLog().getId());

            taskLog.setActive(Boolean.TRUE);
            taskLog.setCreateBy(activityName());
            taskLog.setLastUpdateBy(activityName());
            taskLog.setCreateDate(now);
            taskLog.setLastUpdateDate(now);

            activityLogQueue.put(taskLog);

            boolean keepMessageContent = qifProcess.getQifEventLog().getQifEvent().getKeepMessageContent();

            if (keepMessageContent) {

                if (qifActivityMessage.getMessageContent() != null) {
                    QifActivityLogInputMsg inputMessage = new QifActivityLogInputMsg();
                    inputMessage.setId(StringUtil.random32UUID());
                    inputMessage.setActivityLogId(taskLogId);
                    inputMessage.setMsgType(qifActivityMessage.getMessageType().getName());
                    inputMessage.setActive(Boolean.TRUE);
                    inputMessage.setCreateBy(activityName());
                    inputMessage.setLastUpdateBy(activityName());
                    inputMessage.setCreateDate(now);
                    inputMessage.setLastUpdateDate(now);
                    inputMessage.setInputMessageContent(QifUtil.convertObjectContentToString(
                            qifActivityMessage.getMessageContent(), qifActivityMessage.getMessageType(),
                            jsonPrettyPrint.getObjectMapper()));

                    inputMessageQueue.put(inputMessage);
                }

                if (qifActivityResult != null && qifActivityResult.getResult() != null) {
                    QifActivityLogOutputMsg outputMessage = new QifActivityLogOutputMsg();
                    outputMessage.setId(StringUtil.random32UUID());
                    outputMessage.setActivityLogId(taskLogId);
                    outputMessage.setMsgType(qifActivityResult.getMessageType().getName());
                    outputMessage.setActive(Boolean.TRUE);
                    outputMessage.setCreateBy(activityName());
                    outputMessage.setLastUpdateBy(activityName());
                    outputMessage.setCreateDate(now);
                    outputMessage.setLastUpdateDate(now);
                    outputMessage.setOutputMessageContent(QifUtil.convertObjectContentToString(
                            qifActivityResult.getResult(), qifActivityResult.getMessageType(),
                            jsonPrettyPrint.getObjectMapper()));

                    outputMessageQueue.put(outputMessage);
                }
            } else {
                logger.debug("Task {} keepMessageContent disabled", getClass().getName());
            }
        } else {
            logger.debug("Task {} auditTrail disabled", getClass().getName());
        }
    }

    private void addCounterTask() {
        transactionCounter.add(this.getClass().getName());
        transactionCounter.add(this.getClass().getName() + "_" + WinWorkConstants.SDF_DEFAULT.format(new Date()));
    }
}
