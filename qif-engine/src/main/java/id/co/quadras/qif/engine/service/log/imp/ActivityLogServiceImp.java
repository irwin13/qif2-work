package id.co.quadras.qif.engine.service.log.imp;

import com.google.inject.Inject;
import id.co.quadras.qif.engine.dao.log.ActivityLogDao;
import id.co.quadras.qif.engine.service.log.ActivityLogService;
import id.co.quadras.qif.model.entity.log.QifActivityLog;

import java.util.List;

/**
 * @author irwin Timestamp : 17/05/2014 22:11
 */
public class ActivityLogServiceImp implements ActivityLogService {

    private final ActivityLogDao dao;

    @Inject
    public ActivityLogServiceImp(ActivityLogDao dao) {
        this.dao = dao;
    }

    @Override
    public void batchInsert(List<QifActivityLog> logList) {
        dao.batchInsert(logList);
    }

    @Override
    public void batchUpdate(List<QifActivityLog> logList) {
        dao.batchUpdate(logList);
    }
}
