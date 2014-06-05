package id.co.quadras.qif.dev.dao.imp;

import com.google.inject.Inject;
import com.irwin13.winwork.mybatis.dao.BasicMyBatisDao;
import id.co.quadras.qif.dev.dao.CounterDao;
import id.co.quadras.qif.core.model.entity.QifCounter;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * @author irwin Timestamp : 04/06/2014 18:58
 */
public class CounterDaoImp implements CounterDao {

    private final SqlSessionFactory sqlSessionFactory;
    private final BasicMyBatisDao<QifCounter, String> basicDao;

    @Inject
    public CounterDaoImp(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.basicDao = new BasicMyBatisDao<QifCounter, String>(QifCounter.class, sqlSessionFactory);
    }

    @Override
    public void incrementCounter(QifCounter qifCounter) {
        SqlSession sqlSession = basicDao.openNewSqlSession();
        sqlSession.update(basicDao.getMapperName() + ".updateIncrementSequence", qifCounter);
    }

    @Override
    public void batchInsert(List<QifCounter> qifCounterList) {
        basicDao.batchInsert(qifCounterList);
    }
}
