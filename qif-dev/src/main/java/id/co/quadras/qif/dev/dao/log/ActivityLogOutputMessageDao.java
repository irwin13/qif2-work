package id.co.quadras.qif.dev.dao.log;

import id.co.quadras.qif.model.entity.log.QifActivityLogOutputMsg;

import java.util.List;

/**
 * @author irwin Timestamp : 15/05/2014 22:29
 */
public interface ActivityLogOutputMessageDao {
    public void batchInsert(List<QifActivityLogOutputMsg> logList);
}
