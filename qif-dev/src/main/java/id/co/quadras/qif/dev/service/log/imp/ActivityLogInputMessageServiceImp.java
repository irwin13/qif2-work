package id.co.quadras.qif.dev.service.log.imp;

import com.google.inject.Inject;
import id.co.quadras.qif.dev.dao.log.ActivityLogInputMessageDao;
import id.co.quadras.qif.dev.service.log.ActivityLogInputMessageService;
import id.co.quadras.qif.model.entity.log.QifActivityLogInputMessage;

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
    public void batchInsert(List<QifActivityLogInputMessage> logList) {
        dao.batchInsert(logList);
    }
}
