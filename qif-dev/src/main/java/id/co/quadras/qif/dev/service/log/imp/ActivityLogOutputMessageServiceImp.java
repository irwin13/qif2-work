package id.co.quadras.qif.dev.service.log.imp;

import com.google.inject.Inject;
import id.co.quadras.qif.dev.dao.log.ActivityLogOutputMessageDao;
import id.co.quadras.qif.dev.service.log.ActivityLogOutputMessageService;
import id.co.quadras.qif.model.entity.log.QifActivityLogOutputMessage;

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
    public void batchInsert(List<QifActivityLogOutputMessage> logList) {
        dao.batchInsert(logList);
    }
}
