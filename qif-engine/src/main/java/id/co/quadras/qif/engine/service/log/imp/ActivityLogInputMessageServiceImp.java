package id.co.quadras.qif.engine.service.log.imp;

import com.google.inject.Inject;
import id.co.quadras.qif.core.model.entity.log.QifActivityLogInputMsg;
import id.co.quadras.qif.engine.dao.log.ActivityLogInputMessageDao;
import id.co.quadras.qif.engine.service.log.ActivityLogInputMessageService;

import java.util.List;

/**
 * @author irwin Timestamp : 17/05/2014 22:12
 */
public class ActivityLogInputMessageServiceImp implements ActivityLogInputMessageService {

    private final ActivityLogInputMessageDao dao;

    @Inject
    public ActivityLogInputMessageServiceImp(ActivityLogInputMessageDao dao) {
        this.dao = dao;
    }

    @Override
    public void batchInsert(List<QifActivityLogInputMsg> logList) {
        dao.batchInsert(logList);
    }
}
