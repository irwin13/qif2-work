package id.co.quadras.qif.engine.service.log.imp;

import com.google.inject.Inject;
import id.co.quadras.qif.core.model.entity.log.QifActivityLogOutputMsg;
import id.co.quadras.qif.engine.dao.log.ActivityLogOutputMsgDao;
import id.co.quadras.qif.engine.service.log.ActivityLogOutputMsgService;

import java.util.List;

/**
 * @author irwin Timestamp : 17/05/2014 22:12
 */
public class ActivityLogOutputMsgServiceImp implements ActivityLogOutputMsgService {

    private final ActivityLogOutputMsgDao dao;

    @Inject
    public ActivityLogOutputMsgServiceImp(ActivityLogOutputMsgDao dao) {
        this.dao = dao;
    }

    @Override
    public void batchInsert(List<QifActivityLogOutputMsg> logList) {
        dao.batchInsert(logList);
    }
}
