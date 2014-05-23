package id.co.quadras.qif.dev.service.log.imp;

import com.google.inject.Inject;
import id.co.quadras.qif.dev.dao.log.EventLogMessageDao;
import id.co.quadras.qif.dev.service.log.EventLogMessageService;
import id.co.quadras.qif.model.entity.log.QifEventLogMsg;

import java.util.List;

/**
 * @author irwin Timestamp : 17/05/2014 22:10
 */
public class EventLogMessageServiceImp implements EventLogMessageService {

    private final EventLogMessageDao dao;

    @Inject
    public EventLogMessageServiceImp(EventLogMessageDao dao) {
        this.dao = dao;
    }

    @Override
    public void batchInsert(List<QifEventLogMsg> logList) {
        dao.batchInsert(logList);
    }
}
