package id.co.quadras.qif.dev.service.log;

import id.co.quadras.qif.model.entity.log.QifEventLogMessage;

import java.util.List;

/**
 * @author irwin Timestamp : 14/05/2014 17:23
 */
public interface EventLogMessageService {
    public void batchInsert(List<QifEventLogMessage> logList);
}