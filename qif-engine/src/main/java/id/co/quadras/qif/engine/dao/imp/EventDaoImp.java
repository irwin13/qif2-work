package id.co.quadras.qif.engine.dao.imp;

import com.google.inject.Inject;
import com.irwin13.winwork.mybatis.dao.BasicMyBatisDao;
import id.co.quadras.qif.engine.dao.EventDao;
import id.co.quadras.qif.engine.sqlmap.QifEventSqlmap;
import id.co.quadras.qif.model.entity.QifEvent;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.Arrays;
import java.util.List;

/**
 * @author irwin Timestamp : 15/05/2014 22:48
 */
public class EventDaoImp implements EventDao {

    private final SqlSessionFactory sqlSessionFactory;
    private final BasicMyBatisDao<QifEvent, String> basicDao;

    @Inject
    public EventDaoImp(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.basicDao = new BasicMyBatisDao<QifEvent, String>(QifEvent.class, sqlSessionFactory,
                QifEventSqlmap.class.getName());
    }

    @Override
    public List<QifEvent> select(QifEvent filter) {
        return basicDao.select(filter, null);
    }

    @Override
    public QifEvent selectById(String id) {
        return basicDao.selectById(id, false);
    }

    @Override
    public void update(QifEvent qifEvent) {
        basicDao.update(qifEvent);
    }

    @Override
    public void batchUpdate(SqlSession session, List<QifEvent> modelList) {
        basicDao.batchUpdate(session, modelList);
    }

    @Override
    public SqlSession openSqlSession(ExecutorType executorType) {
        return basicDao.openNewSqlSession(executorType);
    }
}
