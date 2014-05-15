package id.co.quadras.qif.dev.dao.log;

import id.co.quadras.qif.model.entity.log.QifActivityLogInputMessage;

import java.util.List;

/**
 * @author irwin Timestamp : 15/05/2014 22:29
 */
public interface ActivityLogInputMessageDao {
    public void batchInsert(List<QifActivityLogInputMessage> logList);
}
