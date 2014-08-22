package id.co.quadras.qif.engine.service.log;

import id.co.quadras.qif.model.entity.log.QifActivityLogOutputMsg;

import java.util.List;

/**
 * @author irwin Timestamp : 14/05/2014 17:22
 */
public interface ActivityLogOutputMsgService {
    public void batchInsert(List<QifActivityLogOutputMsg> logList);
}
