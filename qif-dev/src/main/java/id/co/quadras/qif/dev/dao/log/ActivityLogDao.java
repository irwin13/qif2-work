package id.co.quadras.qif.dev.dao.log;

import id.co.quadras.qif.model.entity.log.QifActivityLog;

import java.util.List;

/**
 * @author irwin Timestamp : 15/05/2014 22:20
 */
public interface ActivityLogDao {
    public void batchInsert(List<QifActivityLog> logList);
    public void batchUpdate(List<QifActivityLog> logList);
}
