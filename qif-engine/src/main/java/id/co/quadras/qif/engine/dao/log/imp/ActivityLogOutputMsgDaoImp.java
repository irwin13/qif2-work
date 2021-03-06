package id.co.quadras.qif.engine.dao.log.imp;

import com.google.inject.Inject;
import com.irwin13.winwork.mybatis.dao.BasicMyBatisDao;
import id.co.quadras.qif.engine.dao.log.ActivityLogOutputMsgDao;
import id.co.quadras.qif.engine.sqlmap.log.QifActivityLogOutputMsgSqlmap;
import id.co.quadras.qif.model.entity.log.QifActivityLogOutputMsg;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * @author irwin Timestamp : 16/05/2014 23:26
 */
public class ActivityLogOutputMsgDaoImp implements ActivityLogOutputMsgDao {

    private final SqlSessionFactory sqlSessionFactory;
    private final BasicMyBatisDao<QifActivityLogOutputMsg, String> basicDao;

    @Inject
    public ActivityLogOutputMsgDaoImp(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.basicDao = new BasicMyBatisDao<QifActivityLogOutputMsg, String>(QifActivityLogOutputMsg.class,
                sqlSessionFactory, QifActivityLogOutputMsgSqlmap.class.getName());
    }

    @Override
    public void batchInsert(List<QifActivityLogOutputMsg> logList) {
        basicDao.batchInsert(logList);
    }
}
