package id.co.quadras.qif.engine.dao.imp;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.irwin13.winwork.mybatis.dao.BasicMyBatisDao;
import id.co.quadras.qif.engine.dao.CounterDao;
import id.co.quadras.qif.engine.sqlmap.QifCounterSqlmap;
import id.co.quadras.qif.model.entity.QifCounter;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author irwin Timestamp : 04/06/2014 18:58
 */
public class CounterDaoImp implements CounterDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(CounterDaoImp.class);

    private static final String UPDATE_INCREMENT = ".updateIncrementSequence";
    private final SqlSessionFactory sqlSessionFactory;
    private final BasicMyBatisDao<QifCounter, String> basicDao;

    @Inject
    public CounterDaoImp(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.basicDao = new BasicMyBatisDao<QifCounter, String>(QifCounter.class, sqlSessionFactory,
                QifCounterSqlmap.class.getName());
    }

    @Override
    public void incrementCounter(List<QifCounter> qifCounterList) {

        Preconditions.checkNotNull(qifCounterList);
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH);
        try {
            for (QifCounter model : qifCounterList) {
                session.update(basicDao.getMapperName() + UPDATE_INCREMENT, model);
                LOGGER.trace("batch update increment = {}", model);
            }
            session.commit();
        } finally {
            basicDao.closeSqlSession(session);
        }
    }

    @Override
    public void batchInsert(List<QifCounter> qifCounterList) {
        basicDao.batchInsert(qifCounterList);
    }

    @Override
    public void batchUpdate(List<QifCounter> qifCounterList) {
        basicDao.batchUpdate(qifCounterList);
    }

    @Override
    public List<QifCounter> select(QifCounter filter) {
        return basicDao.select(filter, null);
    }

    @Override
    public void insert(QifCounter model) {
        basicDao.insert(model);
    }

    @Override
    public void update(QifCounter model) {
        basicDao.update(model);
    }
}
