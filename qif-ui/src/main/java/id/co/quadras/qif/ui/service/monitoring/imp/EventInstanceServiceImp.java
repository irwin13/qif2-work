package id.co.quadras.qif.ui.service.monitoring.imp;

import com.google.inject.Inject;
import id.co.quadras.qif.ui.dao.monitoring.EventInstanceDao;
import id.co.quadras.qif.ui.dto.monitoring.EventMsg;
import id.co.quadras.qif.ui.service.monitoring.EventInstanceService;

import java.util.List;

/**
 * @author irwin Timestamp : 08/07/2014 14:25
 */
public class EventInstanceServiceImp implements EventInstanceService {

    private final EventInstanceDao dao;

    @Inject
    public EventInstanceServiceImp(EventInstanceDao dao) {
        this.dao = dao;
    }

    @Override
    public List selectEventInstance(int start, int fetchSize) {
        return dao.selectEventInstance(start, fetchSize);
    }

    @Override
    public EventMsg getEventMsg(String id) {
        return dao.getEventMsg(id);
    }

    @Override
    public long selectCountEventInstance() {
        return dao.selectCountEventInstance();
    }
}
