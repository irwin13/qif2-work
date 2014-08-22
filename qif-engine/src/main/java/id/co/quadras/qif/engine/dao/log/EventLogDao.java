package id.co.quadras.qif.engine.dao.log;

import id.co.quadras.qif.model.entity.log.QifEventLog;

import java.util.List;

/**
 * @author irwin Timestamp : 15/05/2014 22:20
 */
public interface EventLogDao {
    public void batchInsert(List<QifEventLog> logList);
}
