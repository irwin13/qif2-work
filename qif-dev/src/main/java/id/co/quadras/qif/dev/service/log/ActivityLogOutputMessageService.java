package id.co.quadras.qif.dev.service.log;

import id.co.quadras.qif.model.entity.log.QifActivityLogOutputMessage;

import java.util.List;

/**
 * @author irwin Timestamp : 14/05/2014 17:22
 */
public interface ActivityLogOutputMessageService {
    public void batchInsert(List<QifActivityLogOutputMessage> logList);
}
