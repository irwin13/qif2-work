package id.co.quadras.qif.dev.dao.log.imp;

import com.google.inject.Inject;
import com.irwin13.winwork.mybatis.dao.BasicMyBatisDao;
import id.co.quadras.qif.dev.dao.log.EventLogDao;
import id.co.quadras.qif.core.model.entity.log.QifEventLog;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * @author irwin Timestamp : 16/05/2014 23:27
 */
public class EventLogDaoImp implements EventLogDao {

    private final SqlSessionFactory sqlSessionFactory;
    private final BasicMyBatisDao<QifEventLog, String> basicDao;

    @Inject
    public EventLogDaoImp(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.basicDao = new BasicMyBatisDao<QifEventLog, String>(QifEventLog.class, sqlSessionFactory);
    }

    @Override
    public void batchInsert(List<QifEventLog> logList) {
        basicDao.batchInsert(logList);
    }
}
