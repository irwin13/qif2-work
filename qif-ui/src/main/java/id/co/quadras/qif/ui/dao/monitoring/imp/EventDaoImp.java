package id.co.quadras.qif.ui.dao.monitoring.imp;

import com.google.inject.Inject;
import com.irwin13.hibernate.dao.BasicHibernateDao;
import id.co.quadras.qif.ui.dao.monitoring.EventDao;
import id.co.quadras.qif.ui.dto.monitoring.EventInstance;
import id.co.quadras.qif.ui.dto.monitoring.EventMsg;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StringType;
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
    @SuppressWarnings("unchecked")
    public List selectEventInstance(int start, int fetchSize) {
        Session session = null;
        List<EventInstance> result = null;

        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("SELECT event_id AS eventId, ");
        sqlQuery.append(" reference_key AS referenceKey, ");
        sqlQuery.append(" node_name AS nodeName, ");
        sqlQuery.append(" event_type AS eventType, ");
        sqlQuery.append(" event_interface AS eventInterface, ");
        sqlQuery.append(" qif_process AS qifProcess ");

        try {
            session = openNewSession();
            result = session.createSQLQuery(sqlQuery.toString())
                    .addScalar("eventId", StringType.INSTANCE)
                    .addScalar("referenceKey", StringType.INSTANCE)
                    .addScalar("nodeName", StringType.INSTANCE)
                    .addScalar("eventType", StringType.INSTANCE)
                    .addScalar("eventInterface", StringType.INSTANCE)
                    .addScalar("qifProcess", StringType.INSTANCE)
                    .setResultTransformer(Transformers.aliasToBean(EventInstance.class))
                    .setFirstResult(start)
                    .setFetchSize(fetchSize)
                    .list();

        } finally {
            closeSession(session);
        }

        return (result == null) ? Collections.EMPTY_LIST : result;
    }

    @Override
    @SuppressWarnings("unchecked")
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
