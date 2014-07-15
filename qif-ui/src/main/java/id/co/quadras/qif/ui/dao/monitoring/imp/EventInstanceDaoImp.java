package id.co.quadras.qif.ui.dao.monitoring.imp;

import com.google.inject.Inject;
import com.irwin13.hibernate.dao.BasicHibernateDao;
import id.co.quadras.qif.ui.dao.monitoring.EventInstanceDao;
import id.co.quadras.qif.ui.dto.monitoring.EventInstance;
import id.co.quadras.qif.ui.dto.monitoring.EventMsg;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.ClobType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 * @author irwin Timestamp : 07/07/2014 17:19
 */
public class EventInstanceDaoImp implements EventInstanceDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventInstanceDaoImp.class);

    private final SessionFactory sessionFactory;
    private final BasicHibernateDao<EventInstance, String> basicDao =
            new BasicHibernateDao<EventInstance, String>(EventInstance.class);

    @Inject
    public EventInstanceDaoImp(SessionFactory sessionFactory) {
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
        sqlQuery.append("SELECT el.id AS id, ");
        sqlQuery.append("e.name AS eventName, ");
        sqlQuery.append("el.reference_key AS referenceKey, ");
        sqlQuery.append("el.node_name AS nodeName, ");
        sqlQuery.append("e.event_type AS eventType, ");
        sqlQuery.append("e.event_interface AS eventInterface, ");
        sqlQuery.append("e.qif_process AS qifProcess, ");
        sqlQuery.append("el.create_date AS createDate ");
        sqlQuery.append("FROM qif_event_log AS el ");
        sqlQuery.append("LEFT JOIN qif_event AS e ON el.event_id = e.id ");
        sqlQuery.append("ORDER BY el.create_date DESC ");

        LOGGER.debug("sql query = {}", sqlQuery.toString());

        try {
            session = openNewSession();
            result = session.createSQLQuery(sqlQuery.toString())
                    .addScalar("id", StringType.INSTANCE)
                    .addScalar("eventName", StringType.INSTANCE)
                    .addScalar("referenceKey", StringType.INSTANCE)
                    .addScalar("nodeName", StringType.INSTANCE)
                    .addScalar("eventType", StringType.INSTANCE)
                    .addScalar("eventInterface", StringType.INSTANCE)
                    .addScalar("qifProcess", StringType.INSTANCE)
                    .addScalar("createDate", TimestampType.INSTANCE)
                    .setResultTransformer(Transformers.aliasToBean(EventInstance.class))
                    .setFirstResult(start)
                    .setMaxResults(fetchSize)
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

        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("SELECT message_content AS messageContent, ");
        sqlQuery.append("msg_type AS msgType, ");
        sqlQuery.append("event_log_id AS eventLogId ");
        sqlQuery.append("FROM qif_event_log_msg ");
        sqlQuery.append("WHERE event_log_id = :eventLogId ");

        try {
            session = openNewSession();
            result = (EventMsg) session.createSQLQuery(sqlQuery.toString())
                    .addScalar("messageContent", StringType.INSTANCE)
                    .addScalar("msgType", StringType.INSTANCE)
                    .setString("eventLogId", id)
                    .setResultTransformer(Transformers.aliasToBean(EventMsg.class))
                    .uniqueResult();
        } finally {
            closeSession(session);
        }

        return result;
    }

    @Override
    public long selectCountEventInstance() {
        Session session = null;
        long result = 0;

        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("SELECT COUNT(id) AS total ");
        sqlQuery.append(" FROM qif_event_log ");
        try {
            session = openNewSession();
            result = (Long) session.createSQLQuery(sqlQuery.toString())
                    .addScalar("total", LongType.INSTANCE)
                    .uniqueResult();

        } finally {
            closeSession(session);
        }

        return result;
    }
}
