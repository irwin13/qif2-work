package id.co.quadras.qif.ui.dao.monitoring.imp;

import com.google.inject.Inject;
import com.irwin13.hibernate.dao.BasicHibernateDao;
import id.co.quadras.qif.ui.dao.monitoring.ProcessInstanceDao;
import id.co.quadras.qif.ui.dto.monitoring.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
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
public class ProcessInstanceDaoImp implements ProcessInstanceDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventInstanceDaoImp.class);

    private final SessionFactory sessionFactory;
    private final BasicHibernateDao<EventInstance, String> basicDao =
            new BasicHibernateDao<EventInstance, String>(EventInstance.class);

    @Inject
    public ProcessInstanceDaoImp(SessionFactory sessionFactory) {
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
    public List<ProcessInstance> selectProcessInstance(int start, int fetchSize) {
        Session session = null;
        List<ProcessInstance> result = null;

        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("SELECT al.id AS id, ");
        sqlQuery.append("e.name AS eventName, ");
        sqlQuery.append("el.node_name AS nodeName, ");
        sqlQuery.append("al.activity_type AS activityType, ");
        sqlQuery.append("al.activity_status AS activityStatus, ");
        sqlQuery.append("al.execution_time AS executionTime, ");
        sqlQuery.append("al.create_date AS createDate, ");
        sqlQuery.append("al.create_by AS createBy ");
        sqlQuery.append("FROM qif_activity_log AS al ");
        sqlQuery.append("LEFT JOIN qif_event_log AS el ON al.event_log_id = el.id ");
        sqlQuery.append("LEFT JOIN qif_event AS e ON el.event_id = e.id ");
        sqlQuery.append("WHERE al.activity_type = 'process' ");
        sqlQuery.append("ORDER BY al.create_date DESC ");

        LOGGER.debug("sql query = {}", sqlQuery.toString());

        try {
            session = openNewSession();
            result = session.createSQLQuery(sqlQuery.toString())
                    .addScalar("id", StringType.INSTANCE)
                    .addScalar("eventName", StringType.INSTANCE)
                    .addScalar("nodeName", StringType.INSTANCE)
                    .addScalar("activityType", StringType.INSTANCE)
                    .addScalar("activityStatus", StringType.INSTANCE)
                    .addScalar("executionTime", LongType.INSTANCE)
                    .addScalar("createDate", TimestampType.INSTANCE)
                    .addScalar("createBy", StringType.INSTANCE)
                    .setResultTransformer(Transformers.aliasToBean(ProcessInstance.class))
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
    public List<ProcessInstanceData> selectActivityData(String processInstanceId) {
        Session session = null;
        List<ProcessInstanceData> result = null;

        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("SELECT data_key AS dataKey, ");
        sqlQuery.append("data_value AS dataValue, ");
        sqlQuery.append("FROM qif_activity_log_data ");
        sqlQuery.append("WHERE activity_log_id = :processId ");

        LOGGER.debug("sql query = {}", sqlQuery.toString());

        try {
            session = openNewSession();
            result = session.createSQLQuery(sqlQuery.toString())
                    .addScalar("dataKey", StringType.INSTANCE)
                    .addScalar("dataValue", StringType.INSTANCE)
                    .setString("processId", processInstanceId)
                    .setResultTransformer(Transformers.aliasToBean(ProcessInstanceData.class))
                    .list();

        } finally {
            closeSession(session);
        }

        return (result == null) ? Collections.EMPTY_LIST : result;
    }

    @Override
    public TaskInputMsg getTaskInputMsg(String processId) {
        Session session = null;
        TaskInputMsg result = null;

        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("SELECT input_message_content AS inputMessageContent, ");
        sqlQuery.append("msg_type AS msgType ");
        sqlQuery.append("FROM qif_activity_log_input_msg ");
        sqlQuery.append("WHERE activity_log_id = :processId ");

        try {
            session = openNewSession();
            result = (TaskInputMsg) session.createSQLQuery(sqlQuery.toString())
                    .addScalar("inputMessageContent", StringType.INSTANCE)
                    .addScalar("msgType", StringType.INSTANCE)
                    .setString("processId", processId)
                    .setResultTransformer(Transformers.aliasToBean(TaskInputMsg.class))
                    .uniqueResult();

        } finally {
            closeSession(session);
        }

        return result;
    }

    @Override
    public TaskOutputMsg getTaskOutputMsg(String processId) {
        Session session = null;
        TaskOutputMsg result = null;

        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("SELECT output_message_content AS outputMessageContent, ");
        sqlQuery.append("msg_type AS msgType ");
        sqlQuery.append("FROM qif_activity_log_output_msg ");
        sqlQuery.append("WHERE activity_log_id = :processId ");

        try {
            session = openNewSession();
            result = (TaskOutputMsg) session.createSQLQuery(sqlQuery.toString())
                    .addScalar("outputMessageContent", StringType.INSTANCE)
                    .addScalar("msgType", StringType.INSTANCE)
                    .setString("processId", processId)
                    .setResultTransformer(Transformers.aliasToBean(TaskOutputMsg.class))
                    .uniqueResult();

        } finally {
            closeSession(session);
        }

        return result;
    }

    @Override
    public long selectCountProcessInstance() {
        Session session = null;
        long result = 0;

        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("SELECT COUNT(id) AS total ");
        sqlQuery.append(" FROM qif_activity_log ");
        sqlQuery.append(" WHERE activity_type = 'process' ");
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

    @Override
    @SuppressWarnings("unchecked")
    public List<ProcessInstance> selectProcessTasks(String processId) {
        Session session = null;
        List<ProcessInstance> result = null;

        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("SELECT al.id AS id, ");
        sqlQuery.append("al.activity_type AS activityType, ");
        sqlQuery.append("al.activity_status AS activityStatus, ");
        sqlQuery.append("al.execution_time AS executionTime, ");
        sqlQuery.append("al.create_date AS createDate, ");
        sqlQuery.append("al.create_by AS createBy ");
        sqlQuery.append("FROM qif_activity_log AS al ");
        sqlQuery.append("WHERE al.parent_activity_id = :processId ");
        sqlQuery.append("ORDER BY al.create_date ");

        LOGGER.debug("sql query = {}", sqlQuery.toString());

        try {
            session = openNewSession();
            result = session.createSQLQuery(sqlQuery.toString())
                    .addScalar("id", StringType.INSTANCE)
                    .addScalar("activityType", StringType.INSTANCE)
                    .addScalar("activityStatus", StringType.INSTANCE)
                    .addScalar("executionTime", LongType.INSTANCE)
                    .addScalar("createDate", TimestampType.INSTANCE)
                    .addScalar("createBy", StringType.INSTANCE)
                    .setString("processId", processId)
                    .setResultTransformer(Transformers.aliasToBean(ProcessInstance.class))
                    .list();

        } finally {
            closeSession(session);
        }

        return (result == null) ? Collections.EMPTY_LIST : result;
    }
}
