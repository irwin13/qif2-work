package id.co.quadras.qif.dev.dao.imp;

import com.google.inject.Inject;
import com.irwin13.winwork.basic.model.SortParameter;
import com.irwin13.winwork.mybatis.dao.BasicMyBatisDao;
import id.co.quadras.qif.dev.dao.EventPropertyDao;
import id.co.quadras.qif.model.entity.QifEventProperty;
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
        this.basicDao = new BasicMyBatisDao<QifEventProperty, String>(QifEventProperty.class, sqlSessionFactory);
    }

    @Override
    public List<QifEventProperty> selectByEvent(String eventId) {
        QifEventProperty filter = new QifEventProperty();
        filter.setQifEventId(eventId);
        return basicDao.select(filter, new SortParameter("property_key", SortParameter.ASC));
    }
}
