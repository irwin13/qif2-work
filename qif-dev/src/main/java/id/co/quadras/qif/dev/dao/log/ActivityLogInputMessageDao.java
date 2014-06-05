package id.co.quadras.qif.dev.dao.log;

import id.co.quadras.qif.core.model.entity.log.QifActivityLogInputMsg;

import java.util.List;

/**
 * @author irwin Timestamp : 15/05/2014 22:29
 */
public interface ActivityLogInputMessageDao {
    public void batchInsert(List<QifActivityLogInputMsg> logList);
}
