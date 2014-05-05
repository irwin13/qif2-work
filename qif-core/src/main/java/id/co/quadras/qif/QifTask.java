package id.co.quadras.qif;

import com.google.inject.Inject;
import com.irwin13.winwork.basic.utilities.StringUtil;
import com.irwin13.winwork.basic.utilities.WinWorkUtil;
import id.co.quadras.qif.helper.JsonParser;
import id.co.quadras.qif.helper.queue.ActivityLogInputMessageQueue;
import id.co.quadras.qif.helper.queue.ActivityLogOutputMessageQueue;
import id.co.quadras.qif.helper.queue.ActivityLogQueue;
import id.co.quadras.qif.model.entity.log.QifActivityLog;
import id.co.quadras.qif.model.entity.log.QifActivityLogData;
import id.co.quadras.qif.model.entity.log.QifActivityLogInputMessage;
import id.co.quadras.qif.model.entity.log.QifActivityLogOutputMessage;
import id.co.quadras.qif.model.vo.QifActivityResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;
import java.util.List;

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

    protected abstract QifActivityResult implementTask(QifActivityMessage qifActivityMessage);

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
    public QifActivityResult executeTask(QifActivityMessage qifActivityMessage) {
        long start = System.currentTimeMillis();
        QifActivityResult bpActivityResult = implementTask(qifActivityMessage);
        insertAuditTrail(qifActivityMessage, start, bpActivityResult);
        return bpActivityResult;
    }

    @Inject
    protected JsonParser jsonParser;

    @Inject
    protected ActivityLogQueue activityLogQueue;

    @Inject
    protected ActivityLogInputMessageQueue inputMessageQueue;

    @Inject
    protected ActivityLogOutputMessageQueue outputMessageQueue;

    private void insertAuditTrail(QifActivityMessage qifActivityMessage, long start, QifActivityResult bpActivityResult) {

        boolean auditTrailEnabled = qifActivityMessage.getQifEventLog().getQifEvent().getAuditTrailEnabled();

        if (auditTrailEnabled) {
            String taskLogId = StringUtil.random32UUID();
            Date today = new Date();

            QifActivityLog taskLog = new QifActivityLog();
            String activityStatus = null;
            List<QifActivityLogData> activityLogDataList = null;

            if (bpActivityResult != null) {
                activityStatus = bpActivityResult.getStatus();
                activityLogDataList = bpActivityResult.getLogDataList();
            }

            taskLog.setId(taskLogId);
            taskLog.setQifEventLog(qifActivityMessage.getQifEventLog());
            taskLog.setActivityStatus(activityStatus);
            taskLog.setActivityType(activityType());
            taskLog.setExecutionTime(System.currentTimeMillis() - start);
            taskLog.setNodeName(WinWorkUtil.getNodeName());
            taskLog.setStarts(start);
            taskLog.setQifActivityLogDataList(activityLogDataList);
            taskLog.setParentActivity(qifActivityMessage.getParentActivityLog());

            taskLog.setCreateBy(activityName());
            taskLog.setLastUpdateBy(activityName());
            taskLog.setCreateDate(today);
            taskLog.setLastUpdateDate(today);

            activityLogQueue.put(taskLog);

            boolean keepMessageContent = qifActivityMessage.getQifEventLog().getQifEvent().getKeepMessageContent();

            if (keepMessageContent) {

                if (qifActivityMessage.getMessage() != null) {
                    QifActivityLogInputMessage inputMessage = new QifActivityLogInputMessage();
                    inputMessage.setId(StringUtil.random32UUID());
                    inputMessage.setActivityLogId(taskLogId);
                    inputMessage.setCreateBy(activityName());
                    inputMessage.setLastUpdateBy(activityName());
                    inputMessage.setCreateDate(today);
                    inputMessage.setLastUpdateDate(today);
                    try {
                        inputMessage.setInputMessageContent(jsonParser.parseToString(
                                true, qifActivityMessage.getMessage()));
                    } catch (IOException e) {
                        logger.error(e.getLocalizedMessage(), e);
                        inputMessage.setInputMessageContent(e.getMessage());
                    }
                    inputMessageQueue.put(inputMessage);
                }

                if (bpActivityResult != null && bpActivityResult.getResult() != null) {
                    QifActivityLogOutputMessage outputMessage = new QifActivityLogOutputMessage();
                    outputMessage.setId(StringUtil.random32UUID());
                    outputMessage.setActivityLogId(taskLogId);
                    outputMessage.setCreateBy(activityName());
                    outputMessage.setLastUpdateBy(activityName());
                    outputMessage.setCreateDate(today);
                    outputMessage.setLastUpdateDate(today);
                    try {
                        outputMessage.setOutputMessageContent(jsonParser
                                .parseToString(true, bpActivityResult.getResult()));
                    } catch (IOException e) {
                        logger.error(e.getLocalizedMessage(), e);
                        outputMessage.setOutputMessageContent(e.getMessage());
                    }
                    outputMessageQueue.put(outputMessage);
                }
            }
        }
    }

}
