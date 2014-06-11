package id.co.quadras.qif.engine.dao.imp;

import com.google.inject.Inject;
import com.irwin13.winwork.basic.model.SortParameter;
import com.irwin13.winwork.mybatis.dao.BasicMyBatisDao;
import id.co.quadras.qif.core.model.entity.QifAdapterProperty;
import id.co.quadras.qif.engine.dao.AdapterPropertyDao;
import id.co.quadras.qif.engine.sqlmap.QifAdapterPropertySqlmap;
import id.co.quadras.qif.engine.sqlmap.QifAdapterSqlmap;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * @author irwin Timestamp : 15/05/2014 22:48
 */
public class AdapterPropertyDaoImp implements AdapterPropertyDao {

    private final SqlSessionFactory sqlSessionFactory;
    private final BasicMyBatisDao<QifAdapterProperty, String> basicDao;

    @Inject
    public AdapterPropertyDaoImp(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.basicDao = new BasicMyBatisDao<QifAdapterProperty, String>(QifAdapterProperty.class, sqlSessionFactory,
                QifAdapterPropertySqlmap.class.getName());
    }

    @Override
    public List<QifAdapterProperty> selectByAdapter(String adapterId) {
        QifAdapterProperty filter = new QifAdapterProperty();
        filter.setQifAdapterId(adapterId);
        return basicDao.select(filter, new SortParameter("property_key", SortParameter.ASC));
    }
}
