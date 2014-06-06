package id.co.quadras.qif.engine.dao.log.imp;

import com.google.inject.Inject;
import com.irwin13.winwork.mybatis.dao.BasicMyBatisDao;
import id.co.quadras.qif.core.model.entity.log.QifActivityLogOutputMsg;
import id.co.quadras.qif.engine.dao.log.ActivityLogOutputMessageDao;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * @author irwin Timestamp : 16/05/2014 23:26
 */
public class ActivityLogOutputMessageDaoImp implements ActivityLogOutputMessageDao {

    private final SqlSessionFactory sqlSessionFactory;
    private final BasicMyBatisDao<QifActivityLogOutputMsg, String> basicDao;

    @Inject
    public ActivityLogOutputMessageDaoImp(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.basicDao = new BasicMyBatisDao<QifActivityLogOutputMsg, String>(QifActivityLogOutputMsg.class, sqlSessionFactory);
    }

    @Override
    public void batchInsert(List<QifActivityLogOutputMsg> logList) {
        basicDao.batchInsert(logList);
    }
}
