package id.co.quadras.qif.engine.dao.log;

import id.co.quadras.qif.core.model.entity.log.QifActivityLogData;

import java.util.List;

/**
 * @author irwin Timestamp : 15/05/2014 22:28
 */
public interface ActivityLogDataDao {
    public void batchInsert(List<QifActivityLogData> logList);
}
