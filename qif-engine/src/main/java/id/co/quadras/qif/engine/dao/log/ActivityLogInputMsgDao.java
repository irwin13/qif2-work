package id.co.quadras.qif.engine.dao.log;

import id.co.quadras.qif.model.entity.log.QifActivityLogInputMsg;

import java.util.List;

/**
 * @author irwin Timestamp : 15/05/2014 22:29
 */
public interface ActivityLogInputMsgDao {
    public void batchInsert(List<QifActivityLogInputMsg> logList);
}
