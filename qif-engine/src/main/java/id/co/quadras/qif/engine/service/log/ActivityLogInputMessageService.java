package id.co.quadras.qif.engine.service.log;

import id.co.quadras.qif.core.model.entity.log.QifActivityLogInputMsg;

import java.util.List;

/**
 * @author irwin Timestamp : 14/05/2014 17:22
 */
public interface ActivityLogInputMessageService {
    public void batchInsert(List<QifActivityLogInputMsg> logList);
}
