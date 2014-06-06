package id.co.quadras.qif.engine.service.log.imp;

import com.google.inject.Inject;
import id.co.quadras.qif.core.model.entity.log.QifActivityLogOutputMsg;
import id.co.quadras.qif.engine.dao.log.ActivityLogOutputMessageDao;
import id.co.quadras.qif.engine.service.log.ActivityLogOutputMessageService;

import java.util.List;

/**
 * @author irwin Timestamp : 17/05/2014 22:12
 */
public class ActivityLogOutputMessageServiceImp implements ActivityLogOutputMessageService {

    private final ActivityLogOutputMessageDao dao;

    @Inject
    public ActivityLogOutputMessageServiceImp(ActivityLogOutputMessageDao dao) {
        this.dao = dao;
    }

    @Override
    public void batchInsert(List<QifActivityLogOutputMsg> logList) {
        dao.batchInsert(logList);
    }
}
