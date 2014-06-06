package id.co.quadras.qif.engine.dao.log.imp;

import com.google.inject.Inject;
import com.irwin13.winwork.mybatis.dao.BasicMyBatisDao;
import id.co.quadras.qif.core.model.entity.log.QifActivityLogInputMsg;
import id.co.quadras.qif.engine.dao.log.ActivityLogInputMessageDao;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * @author irwin Timestamp : 16/05/2014 23:25
 */
public class ActivityLogInputMessageDaoImp implements ActivityLogInputMessageDao {

    private final SqlSessionFactory sqlSessionFactory;
    private final BasicMyBatisDao<QifActivityLogInputMsg, String> basicDao;

    @Inject
    public ActivityLogInputMessageDaoImp(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.basicDao = new BasicMyBatisDao<QifActivityLogInputMsg, String>(QifActivityLogInputMsg.class, sqlSessionFactory);
    }

    @Override
    public void batchInsert(List<QifActivityLogInputMsg> logList) {
        basicDao.batchInsert(logList);
    }
}
