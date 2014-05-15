package id.co.quadras.qif.dev.dao.log;

import id.co.quadras.qif.model.entity.log.QifActivityLogData;

import java.util.List;

/**
 * @author irwin Timestamp : 15/05/2014 22:28
 */
public interface ActivityLogDataDao {
    public void batchInsert(List<QifActivityLogData> logList);
}
