package id.co.quadras.qif.engine.dao.imp;

import com.google.inject.Inject;
import com.irwin13.winwork.basic.model.SortParameter;
import com.irwin13.winwork.mybatis.dao.BasicMyBatisDao;
import id.co.quadras.qif.core.model.entity.QifEventProperty;
import id.co.quadras.qif.engine.dao.EventPropertyDao;
import id.co.quadras.qif.engine.sqlmap.QifEventPropertySqlmap;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * @author irwin Timestamp : 15/05/2014 22:48
 */
public class EventPropertyDaoImp implements EventPropertyDao {

    private final SqlSessionFactory sqlSessionFactory;
    private final BasicMyBatisDao<QifEventProperty, String> basicDao;

    @Inject
    public EventPropertyDaoImp(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.basicDao = new BasicMyBatisDao<QifEventProperty, String>(QifEventProperty.class, sqlSessionFactory,
                QifEventPropertySqlmap.class.getName());
    }

    @Override
    public List<QifEventProperty> selectByEvent(String eventId) {
        QifEventProperty filter = new QifEventProperty();
        filter.setQifEventId(eventId);
        return basicDao.select(filter, new SortParameter("property_key", SortParameter.ASC));
    }

    @Override
    public List<QifEventProperty> select(QifEventProperty filter) {
        return basicDao.select(filter, null);
    }

    @Override
    public void batchUpdate(SqlSession session, List<QifEventProperty> modelList) {
        basicDao.batchUpdate(session, modelList);
    }
}
