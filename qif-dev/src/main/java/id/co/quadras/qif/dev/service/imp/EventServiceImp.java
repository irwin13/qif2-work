package id.co.quadras.qif.dev.service.imp;

import com.google.inject.Inject;
import id.co.quadras.qif.dev.dao.EventDao;
import id.co.quadras.qif.dev.dao.EventPropertyDao;
import id.co.quadras.qif.dev.service.EventService;
import id.co.quadras.qif.exception.QifException;
import id.co.quadras.qif.model.entity.QifEvent;
import id.co.quadras.qif.model.entity.QifEventProperty;

import java.util.List;

/**
 * @author irwin Timestamp : 17/05/2014 22:06
 */
public class EventServiceImp implements EventService {

    private final EventDao eventDao;
    private final EventPropertyDao eventPropertyDao;

    @Inject
    public EventServiceImp(EventDao eventDao, EventPropertyDao eventPropertyDao) {
        this.eventDao = eventDao;
        this.eventPropertyDao = eventPropertyDao;
    }

    @Override
    public QifEvent selectById(String id) {
        QifEvent qifEvent = eventDao.selectById(id);
        if (qifEvent == null) {
            throw new QifException("FATAL : Missing QifEvent with id " + id + " in database");
        } else {
            List<QifEventProperty> propertyList = eventPropertyDao.selectByEvent(id);
            qifEvent.setQifEventPropertyList(propertyList);
        }
        return qifEvent;
    }
}
