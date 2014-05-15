package id.co.quadras.qif.dev.dao.log;

import id.co.quadras.qif.model.entity.log.QifEventLogMessage;

import java.util.List;

/**
 * @author irwin Timestamp : 15/05/2014 22:20
 */
public interface EventLogMessageDao {
    public void batchInsert(List<QifEventLogMessage> logList);
}
