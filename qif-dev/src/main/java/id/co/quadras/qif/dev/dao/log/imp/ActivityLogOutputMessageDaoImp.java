package id.co.quadras.qif.dev.dao.log.imp;

import com.google.inject.Inject;
import com.irwin13.winwork.mybatis.dao.BasicMyBatisDao;
import id.co.quadras.qif.dev.dao.log.ActivityLogOutputMessageDao;
import id.co.quadras.qif.model.entity.log.QifActivityLogOutputMessage;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * @author irwin Timestamp : 16/05/2014 23:26
 */
public class ActivityLogOutputMessageDaoImp implements ActivityLogOutputMessageDao {

    private final SqlSessionFactory sqlSessionFactory;
    private final BasicMyBatisDao<QifActivityLogOutputMessage, String> basicDao;

    @Inject
    public ActivityLogOutputMessageDaoImp(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.basicDao = new BasicMyBatisDao<QifActivityLogOutputMessage, String>(QifActivityLogOutputMessage.class, sqlSessionFactory);
    }

    @Override
    public void batchInsert(List<QifActivityLogOutputMessage> logList) {
        basicDao.batchInsert(logList);
    }
}
