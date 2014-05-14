package id.co.quadras.qif.dev.service.log;

import id.co.quadras.qif.model.entity.log.QifEventLog;

import java.util.List;

/**
 * @author irwin Timestamp : 14/05/2014 17:22
 */
public interface EventLogService {
    public void batchInsert(List<QifEventLog> logList);
}
