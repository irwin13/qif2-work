package id.co.quadras.qif.engine.dao.log.imp;

import com.google.inject.Inject;
import com.irwin13.winwork.mybatis.dao.BasicMyBatisDao;
import id.co.quadras.qif.engine.dao.log.EventLogMsgDao;
import id.co.quadras.qif.engine.sqlmap.log.QifEventLogMsgSqlmap;
import id.co.quadras.qif.model.entity.log.QifEventLogMsg;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * @author irwin Timestamp : 16/05/2014 23:29
 */
public class EventLogMsgDaoImp implements EventLogMsgDao {

    private final SqlSessionFactory sqlSessionFactory;
    private final BasicMyBatisDao<QifEventLogMsg, String> basicDao;

    @Inject
    public EventLogMsgDaoImp(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.basicDao = new BasicMyBatisDao<QifEventLogMsg, String>(QifEventLogMsg.class, sqlSessionFactory,
                QifEventLogMsgSqlmap.class.getName());
    }

    @Override
    public void batchInsert(List<QifEventLogMsg> logList) {
        basicDao.batchInsert(logList);
    }

}
