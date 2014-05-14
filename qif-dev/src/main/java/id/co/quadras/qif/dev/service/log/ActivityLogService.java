package id.co.quadras.qif.dev.service.log;

import id.co.quadras.qif.model.entity.log.QifActivityLog;

import java.util.List;

/**
 * @author irwin Timestamp : 14/05/2014 17:22
 */
public interface ActivityLogService {
    public void batchInsert(List<QifActivityLog> logList);
    public void batchUpdate(List<QifActivityLog> logList);
}
