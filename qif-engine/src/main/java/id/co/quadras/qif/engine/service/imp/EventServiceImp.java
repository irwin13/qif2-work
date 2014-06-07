package id.co.quadras.qif.engine.service.imp;

import com.google.inject.Inject;
import id.co.quadras.qif.core.exception.QifException;
import id.co.quadras.qif.core.model.entity.QifEvent;
import id.co.quadras.qif.core.model.entity.QifEventProperty;
import id.co.quadras.qif.engine.dao.EventDao;
import id.co.quadras.qif.engine.dao.EventPropertyDao;
import id.co.quadras.qif.engine.service.EventService;

import java.util.LinkedList;
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

    @Override
    public List<QifEvent> select(QifEvent filter) {
        List<QifEvent> result = new LinkedList<QifEvent>();
        List<QifEvent> list = eventDao.select(filter);
        for (QifEvent qifEvent : list) {
            QifEvent event = selectById(qifEvent.getId());
            result.add(event);
        }
        return result;
    }

    @Override
    public QifEvent selectByProperty(String propertyKey, String propertyValue) {
        QifEvent qifEvent = null;
        QifEventProperty propertyFilter = new QifEventProperty();
        propertyFilter.setPropertyKey(propertyKey);
        propertyFilter.setPropertyValue(propertyValue);

        List<QifEventProperty> propertyList = eventPropertyDao.select(propertyFilter);
        if (propertyList != null && !propertyList.isEmpty()) {
            QifEventProperty property = propertyList.get(0);
            qifEvent = selectById(property.getQifEventId());
        }
        return qifEvent;
    }

    @Override
    public List<QifEvent> selectByPropertyKey(String propertyKey) {
        List<QifEvent> eventList = new LinkedList<QifEvent>();
        QifEventProperty propertyFilter = new QifEventProperty();
        propertyFilter.setPropertyKey(propertyKey);

        List<QifEventProperty> propertyList = eventPropertyDao.select(propertyFilter);
        if (propertyList != null && !propertyList.isEmpty()) {
            for (QifEventProperty property : propertyList) {
                QifEvent qifEvent = selectById(property.getQifEventId());
                eventList.add(qifEvent);
            }

        }
        return eventList;
    }
}
