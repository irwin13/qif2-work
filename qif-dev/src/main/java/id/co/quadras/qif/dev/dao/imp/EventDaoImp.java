package id.co.quadras.qif.dev.dao.imp;

import com.google.inject.Inject;
import com.irwin13.winwork.mybatis.dao.BasicMyBatisDao;
import id.co.quadras.qif.dev.dao.EventDao;
import id.co.quadras.qif.core.model.entity.QifEvent;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * @author irwin Timestamp : 15/05/2014 22:48
 */
public class EventDaoImp implements EventDao {

    private final SqlSessionFactory sqlSessionFactory;
    private final BasicMyBatisDao<QifEvent, String> basicDao;

    @Inject
    public EventDaoImp(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.basicDao = new BasicMyBatisDao<QifEvent, String>(QifEvent.class, sqlSessionFactory);
    }

    @Override
    public List<QifEvent> select(QifEvent filter) {
        return basicDao.select(filter, null);
    }

    @Override
    public QifEvent selectById(String id) {
        return basicDao.selectById(id, false);
    }
}
