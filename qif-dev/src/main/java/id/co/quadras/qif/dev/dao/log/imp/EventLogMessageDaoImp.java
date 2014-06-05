package id.co.quadras.qif.dev.dao.log.imp;

import com.google.inject.Inject;
import com.irwin13.winwork.mybatis.dao.BasicMyBatisDao;
import id.co.quadras.qif.dev.dao.log.EventLogMessageDao;
import id.co.quadras.qif.core.model.entity.log.QifEventLogMsg;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * @author irwin Timestamp : 16/05/2014 23:29
 */
public class EventLogMessageDaoImp implements EventLogMessageDao {

    private final SqlSessionFactory sqlSessionFactory;
    private final BasicMyBatisDao<QifEventLogMsg, String> basicDao;

    @Inject
    public EventLogMessageDaoImp(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.basicDao = new BasicMyBatisDao<QifEventLogMsg, String>(QifEventLogMsg.class, sqlSessionFactory);
    }

    @Override
    public void batchInsert(List<QifEventLogMsg> logList) {
        basicDao.batchInsert(logList);
    }

}
