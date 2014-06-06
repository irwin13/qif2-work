package id.co.quadras.qif.engine.service.log.imp;

import com.google.inject.Inject;
import id.co.quadras.qif.core.model.entity.log.QifEventLog;
import id.co.quadras.qif.engine.dao.log.EventLogDao;
import id.co.quadras.qif.engine.service.log.EventLogService;

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
