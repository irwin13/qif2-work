package id.co.quadras.qif.engine.service.log.imp;

import com.google.inject.Inject;
import id.co.quadras.qif.core.model.entity.log.QifEventLogMsg;
import id.co.quadras.qif.engine.dao.log.EventLogMsgDao;
import id.co.quadras.qif.engine.service.log.EventLogMsgService;

import java.util.List;

/**
 * @author irwin Timestamp : 17/05/2014 22:10
 */
public class EventLogMsgServiceImp implements EventLogMsgService {

    private final EventLogMsgDao dao;

    @Inject
    public EventLogMsgServiceImp(EventLogMsgDao dao) {
        this.dao = dao;
    }

    @Override
    public void batchInsert(List<QifEventLogMsg> logList) {
        dao.batchInsert(logList);
    }
}
