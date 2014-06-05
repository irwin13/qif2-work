package id.co.quadras.qif.core;

import com.google.inject.Inject;
import com.irwin13.winwork.basic.WinWorkConstants;
import com.irwin13.winwork.basic.utilities.StringUtil;
import com.irwin13.winwork.basic.utilities.WinWorkUtil;
import id.co.quadras.qif.core.helper.JsonParser;
import id.co.quadras.qif.core.helper.QifTransactionCounter;
import id.co.quadras.qif.core.helper.queue.ActivityLogInputMsgQueue;
import id.co.quadras.qif.core.helper.queue.ActivityLogOutputMsgQueue;
import id.co.quadras.qif.core.helper.queue.ActivityLogQueue;
import id.co.quadras.qif.core.model.entity.log.QifActivityLog;
import id.co.quadras.qif.core.model.entity.log.QifActivityLogData;
import id.co.quadras.qif.core.model.entity.log.QifActivityLogInputMsg;
import id.co.quadras.qif.core.model.entity.log.QifActivityLogOutputMsg;
import id.co.quadras.qif.core.model.vo.QifActivityResult;
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
public abstract class QifTask implements QifActivity {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public static final String TYPE = "task";

    @Override
    public String activityType() {
        return TYPE;
    }

    protected abstract QifActivityResult implementTask(QifTaskMessage qifTaskMessage);

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
     * @param qifTaskMessage
     * @return
     */
    public QifActivityResult executeTask(QifTaskMessage qifTaskMessage) {
        long start = System.currentTimeMillis();
        QifActivityResult bpActivityResult = implementTask(qifTaskMessage);
        insertAuditTrail(qifTaskMessage, start, bpActivityResult);
        return bpActivityResult;
    }

    @Inject
    protected JsonParser jsonParser;

    @Inject
    protected QifTransactionCounter transactionCounter;

    @Inject
    private ActivityLogQueue activityLogQueue;

    @Inject
    private ActivityLogInputMsgQueue inputMessageQueue;

    @Inject
    private ActivityLogOutputMsgQueue outputMessageQueue;

    private void insertAuditTrail(QifTaskMessage qifTaskMessage, long start, QifActivityResult qifActivityResult) {

        boolean auditTrailEnabled = qifTaskMessage.getQifProcess()
                                        .getQifEventLog().getQifEvent().getAuditTrailEnabled();

        if (auditTrailEnabled) {
            String taskLogId = StringUtil.random32UUID();
            Date today = new Date();

            QifActivityLog taskLog = new QifActivityLog();
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
                        logData.setActivityLogId(taskLogId);
                        activityLogDataList.add(logData);
                    }
                }

            }

            taskLog.setId(taskLogId);
            taskLog.setQifEventLog(qifTaskMessage.getQifProcess().getQifEventLog());
            taskLog.setActivityStatus(activityStatus);
            taskLog.setActivityType(activityType());
            taskLog.setExecutionTime(System.currentTimeMillis() - start);
            taskLog.setNodeName(WinWorkUtil.getNodeName());
            taskLog.setStartTime(start);
            taskLog.setQifActivityLogDataList(activityLogDataList);
            taskLog.setParentActivity(qifTaskMessage.getQifProcess().getProcessActivityLog());
            taskLog.setParentActivityId(qifTaskMessage.getQifProcess().getProcessActivityLog().getId());

            taskLog.setActive(Boolean.TRUE);
            taskLog.setCreateBy(activityName());
            taskLog.setLastUpdateBy(activityName());
            taskLog.setCreateDate(today);
            taskLog.setLastUpdateDate(today);

            activityLogQueue.put(taskLog);

            boolean keepMessageContent = qifTaskMessage.getQifProcess()
                                            .getQifEventLog().getQifEvent().getKeepMessageContent();

            if (keepMessageContent) {

                if (qifTaskMessage.getMessage() != null) {
                    QifActivityLogInputMsg inputMessage = new QifActivityLogInputMsg();
                    inputMessage.setId(StringUtil.random32UUID());
                    inputMessage.setActivityLogId(taskLogId);

                    inputMessage.setActive(Boolean.TRUE);
                    inputMessage.setCreateBy(activityName());
                    inputMessage.setLastUpdateBy(activityName());
                    inputMessage.setCreateDate(today);
                    inputMessage.setLastUpdateDate(today);
                    try {
                        inputMessage.setInputMessageContent(jsonParser.parseToString(
                                true, qifTaskMessage.getMessage()));
                    } catch (IOException e) {
                        logger.error(e.getLocalizedMessage(), e);
                        inputMessage.setInputMessageContent(e.getMessage());
                    }
                    inputMessageQueue.put(inputMessage);
                }

                if (qifActivityResult != null && qifActivityResult.getResult() != null) {
                    QifActivityLogOutputMsg outputMessage = new QifActivityLogOutputMsg();
                    outputMessage.setId(StringUtil.random32UUID());
                    outputMessage.setActivityLogId(taskLogId);

                    outputMessage.setActive(Boolean.TRUE);
                    outputMessage.setCreateBy(activityName());
                    outputMessage.setLastUpdateBy(activityName());
                    outputMessage.setCreateDate(today);
                    outputMessage.setLastUpdateDate(today);
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

    private void addCounterTask() {
        transactionCounter.add(this.getClass().getName());
        transactionCounter.add(this.getClass().getName() + "_" + WinWorkConstants.SDF_DEFAULT.format(new Date()));
    }
}
