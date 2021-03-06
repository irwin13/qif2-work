package id.co.quadras.qif.engine.dao.log.imp;

import com.google.inject.Inject;
import com.irwin13.winwork.mybatis.dao.BasicMyBatisDao;
import id.co.quadras.qif.engine.dao.log.ActivityLogDao;
import id.co.quadras.qif.engine.sqlmap.log.QifActivityLogSqlmap;
import id.co.quadras.qif.model.entity.log.QifActivityLog;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * @author irwin Timestamp : 16/05/2014 23:19
 */
public class ActivityLogDaoImp implements ActivityLogDao {

    private final SqlSessionFactory sqlSessionFactory;
    private final BasicMyBatisDao<QifActivityLog, String> basicDao;

    @Inject
    public ActivityLogDaoImp(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.basicDao = new BasicMyBatisDao<QifActivityLog, String>(QifActivityLog.class, sqlSessionFactory,
                QifActivityLogSqlmap.class.getName());
    }

    @Override
    public void batchInsert(List<QifActivityLog> logList) {
        basicDao.batchInsert(logList);
    }

    @Override
    public void batchUpdate(List<QifActivityLog> logList) {
        basicDao.batchUpdate(logList);
    }
}
