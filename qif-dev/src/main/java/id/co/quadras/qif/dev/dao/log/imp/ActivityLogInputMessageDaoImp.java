package id.co.quadras.qif.dev.dao.log.imp;

import com.google.inject.Inject;
import com.irwin13.winwork.mybatis.dao.BasicMyBatisDao;
import id.co.quadras.qif.dev.dao.log.ActivityLogInputMessageDao;
import id.co.quadras.qif.model.entity.log.QifActivityLogInputMessage;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * @author irwin Timestamp : 16/05/2014 23:25
 */
public class ActivityLogInputMessageDaoImp implements ActivityLogInputMessageDao {

    private final SqlSessionFactory sqlSessionFactory;
    private final BasicMyBatisDao<QifActivityLogInputMessage, String> basicDao;

    @Inject
    public ActivityLogInputMessageDaoImp(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.basicDao = new BasicMyBatisDao<QifActivityLogInputMessage, String>(QifActivityLogInputMessage.class, sqlSessionFactory);
    }

    @Override
    public void batchInsert(List<QifActivityLogInputMessage> logList) {
        basicDao.batchInsert(logList);
    }
}