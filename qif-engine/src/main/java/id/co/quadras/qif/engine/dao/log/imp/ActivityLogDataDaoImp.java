package id.co.quadras.qif.engine.dao.log.imp;

import com.google.inject.Inject;
import com.irwin13.winwork.mybatis.dao.BasicMyBatisDao;
import id.co.quadras.qif.engine.dao.log.ActivityLogDataDao;
import id.co.quadras.qif.engine.sqlmap.log.QifActivityLogDataSqlmap;
import id.co.quadras.qif.model.entity.log.QifActivityLogData;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * @author irwin Timestamp : 16/05/2014 23:22
 */
public class ActivityLogDataDaoImp implements ActivityLogDataDao {

    private final SqlSessionFactory sqlSessionFactory;
    private final BasicMyBatisDao<QifActivityLogData, String> basicDao;

    @Inject
    public ActivityLogDataDaoImp(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.basicDao = new BasicMyBatisDao<QifActivityLogData, String>(QifActivityLogData.class, sqlSessionFactory,
                QifActivityLogDataSqlmap.class.getName());
    }

    @Override
    public void batchInsert(List<QifActivityLogData> logList) {
        basicDao.batchInsert(logList);
    }
}
