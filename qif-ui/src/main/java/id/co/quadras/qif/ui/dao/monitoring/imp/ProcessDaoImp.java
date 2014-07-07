package id.co.quadras.qif.ui.dao.monitoring.imp;

import com.google.inject.Inject;
import com.irwin13.hibernate.dao.BasicHibernateDao;
import id.co.quadras.qif.ui.dao.monitoring.ProcessDao;
import id.co.quadras.qif.ui.dto.monitoring.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 * @author irwin Timestamp : 07/07/2014 17:19
 */
public class ProcessDaoImp implements ProcessDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventDaoImp.class);

    private final SessionFactory sessionFactory;
    private final BasicHibernateDao<EventInstance, String> basicDao =
            new BasicHibernateDao<EventInstance, String>(EventInstance.class);

    @Inject
    public ProcessDaoImp(SessionFactory sessionFactory) {
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
    public List<ProcessInstance> selectProcessInstance(int start, int fetchSize) {
        Session session = null;
        List<ProcessInstance> result = null;

        try {
            session = openNewSession();
        } finally {
            closeSession(session);
        }

        return (result == null) ? Collections.EMPTY_LIST : result;
    }

    @Override
    public List<ProcessInstanceData> selectProcessData(String processInstanceId) {
        Session session = null;
        List<ProcessInstanceData> result = null;

        try {
            session = openNewSession();
        } finally {
            closeSession(session);
        }

        return (result == null) ? Collections.EMPTY_LIST : result;
    }

    @Override
    public TaskInputMsg getTaskInputMsg(String processId) {
        Session session = null;
        TaskInputMsg result = null;

        try {
            session = openNewSession();
        } finally {
            closeSession(session);
        }

        return result;
    }

    @Override
    public TaskOutputMsg getTaskOutputMsg(String processId) {
        Session session = null;
        TaskOutputMsg result = null;

        try {
            session = openNewSession();
        } finally {
            closeSession(session);
        }

        return result;
    }
}
