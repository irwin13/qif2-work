package id.co.quadras.qif.engine.dao.log;

import id.co.quadras.qif.core.model.entity.log.QifEventLogMsg;

import java.util.List;

/**
 * @author irwin Timestamp : 15/05/2014 22:20
 */
public interface EventLogMsgDao {
    public void batchInsert(List<QifEventLogMsg> logList);
}
