package id.co.quadras.qif.engine.service.log;

import id.co.quadras.qif.model.entity.log.QifEventLogMsg;

import java.util.List;

/**
 * @author irwin Timestamp : 14/05/2014 17:23
 */
public interface EventLogMsgService {
    public void batchInsert(List<QifEventLogMsg> logList);
}
