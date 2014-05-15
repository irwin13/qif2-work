package id.co.quadras.qif.dev.dao.imp;

import com.google.inject.Inject;
import com.irwin13.winwork.mybatis.dao.BasicMyBatisDao;
import id.co.quadras.qif.dev.dao.AdapterPropertyDao;
import id.co.quadras.qif.model.entity.QifAdapterProperty;
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
        this.basicDao = new BasicMyBatisDao<QifAdapterProperty, String>(QifAdapterProperty.class, sqlSessionFactory);
    }

    @Override
    public List<QifAdapterProperty> selectByAdapter(String adapterId) {
        return null;
    }
}
