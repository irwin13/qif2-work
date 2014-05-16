package id.co.quadras.qif.dev.dao.log.imp;

import com.google.inject.Inject;
import com.irwin13.winwork.mybatis.dao.BasicMyBatisDao;
import id.co.quadras.qif.dev.dao.log.EventLogMessageDao;
import id.co.quadras.qif.model.entity.log.QifEventLogMessage;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * @author irwin Timestamp : 16/05/2014 23:29
 */
public class EventLogMessageDaoImp implements EventLogMessageDao {

    private final SqlSessionFactory sqlSessionFactory;
    private final BasicMyBatisDao<QifEventLogMessage, String> basicDao;

    @Inject
    public EventLogMessageDaoImp(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.basicDao = new BasicMyBatisDao<QifEventLogMessage, String>(QifEventLogMessage.class, sqlSessionFactory);
    }

    @Override
    public void batchInsert(List<QifEventLogMessage> logList) {
        basicDao.batchInsert(logList);
    }

}
