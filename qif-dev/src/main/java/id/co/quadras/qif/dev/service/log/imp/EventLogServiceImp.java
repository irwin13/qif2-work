package id.co.quadras.qif.dev.service.log.imp;

import com.google.inject.Inject;
import id.co.quadras.qif.dev.dao.log.EventLogDao;
import id.co.quadras.qif.dev.service.log.EventLogService;
import id.co.quadras.qif.model.entity.log.QifEventLog;

import java.util.List;

/**
 * @author irwin Timestamp : 17/05/2014 22:09
 */
public class EventLogServiceImp implements EventLogService {

    private final EventLogDao dao;

    @Inject
    public EventLogServiceImp(EventLogDao dao) {
        this.dao = dao;
    }

    @Override
    public void batchInsert(List<QifEventLog> logList) {
        dao.batchInsert(logList);
    }
}
