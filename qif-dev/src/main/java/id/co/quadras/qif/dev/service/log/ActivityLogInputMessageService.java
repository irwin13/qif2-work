package id.co.quadras.qif.dev.service.log;

import id.co.quadras.qif.model.entity.log.QifActivityLogInputMessage;

import java.util.List;

/**
 * @author irwin Timestamp : 14/05/2014 17:22
 */
public interface ActivityLogInputMessageService {
    public void batchInsert(List<QifActivityLogInputMessage> logList);
}
