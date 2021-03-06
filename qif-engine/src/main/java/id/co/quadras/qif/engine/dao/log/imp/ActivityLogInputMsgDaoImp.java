package id.co.quadras.qif.engine.dao.log.imp;

import com.google.inject.Inject;
import com.irwin13.winwork.mybatis.dao.BasicMyBatisDao;
import id.co.quadras.qif.engine.dao.log.ActivityLogInputMsgDao;
import id.co.quadras.qif.engine.sqlmap.log.QifActivityLogInputMsgSqlmap;
import id.co.quadras.qif.model.entity.log.QifActivityLogInputMsg;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * @author irwin Timestamp : 16/05/2014 23:25
 */
public class ActivityLogInputMsgDaoImp implements ActivityLogInputMsgDao {

    private final SqlSessionFactory sqlSessionFactory;
    private final BasicMyBatisDao<QifActivityLogInputMsg, String> basicDao;

    @Inject
    public ActivityLogInputMsgDaoImp(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.basicDao = new BasicMyBatisDao<QifActivityLogInputMsg, String>(QifActivityLogInputMsg.class,
                sqlSessionFactory, QifActivityLogInputMsgSqlmap.class.getName());
    }

    @Override
    public void batchInsert(List<QifActivityLogInputMsg> logList) {
        basicDao.batchInsert(logList);
    }
}
