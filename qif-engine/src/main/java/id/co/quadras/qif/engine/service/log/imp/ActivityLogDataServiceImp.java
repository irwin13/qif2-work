package id.co.quadras.qif.engine.service.log.imp;

import com.google.inject.Inject;
import id.co.quadras.qif.core.model.entity.log.QifActivityLogData;
import id.co.quadras.qif.engine.dao.log.ActivityLogDataDao;
import id.co.quadras.qif.engine.service.log.ActivityLogDataService;

import java.util.List;

/**
 * @author irwin Timestamp : 17/05/2014 22:11
 */
public class ActivityLogDataServiceImp implements ActivityLogDataService {

    private final ActivityLogDataDao dao;

    @Inject
    public ActivityLogDataServiceImp(ActivityLogDataDao dao) {
        this.dao = dao;
    }

    @Override
    public void batchInsert(List<QifActivityLogData> logList) {
        dao.batchInsert(logList);
    }
}
