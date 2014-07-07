package id.co.quadras.qif.ui.dao.monitoring.imp;

import com.google.inject.Inject;
import com.irwin13.hibernate.dao.BasicHibernateDao;
import id.co.quadras.qif.ui.dao.monitoring.EventDao;
import id.co.quadras.qif.ui.dto.monitoring.EventInstance;
import id.co.quadras.qif.ui.dto.monitoring.EventMsg;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 * @author irwin Timestamp : 07/07/2014 17:19
 */
public class EventDaoImp implements EventDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventDaoImp.class);

    private final SessionFactory sessionFactory;
    private final BasicHibernateDao<EventInstance, String> basicDao =
            new BasicHibernateDao<EventInstance, String>(EventInstance.class);

    @Inject
    public EventDaoImp(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        basicDao.setSessionFactory(sessionFactory);
    }

    public Session openNewSession() {
        return basicDao.openNewSession();
    }

    public void closeSession(Session session) {
        basicDao.closeSession(session);
    }

    @Override
    public List<EventInstance> selectEventInstance(int start, int fetchSize) {
        Session session = null;
        List<EventInstance> result = null;

        StringBuilder query = new StringBuilder();
        query.append("SELECT ");
        try {
            session = openNewSession();

        } finally {
            closeSession(session);
        }

        return (result == null) ? Collections.EMPTY_LIST : result;
    }

    @Override
    public EventMsg getEventMsg(String id) {
        Session session = null;
        EventMsg result = null;

        try {
            session = openNewSession();
        } finally {
            closeSession(session);
        }

        return result;
    }
}
