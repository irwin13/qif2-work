package id.co.quadras.qif.engine.dao.imp;

import com.google.inject.Inject;
import com.irwin13.winwork.mybatis.dao.BasicMyBatisDao;
import id.co.quadras.qif.engine.dao.AdapterDao;
import id.co.quadras.qif.engine.sqlmap.QifAdapterSqlmap;
import id.co.quadras.qif.model.entity.QifAdapter;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * @author irwin Timestamp : 15/05/2014 22:40
 */
public class AdapterDaoImp implements AdapterDao {

    private final SqlSessionFactory sqlSessionFactory;
    private final BasicMyBatisDao<QifAdapter, String> basicDao;

    @Inject
    public AdapterDaoImp(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.basicDao = new BasicMyBatisDao<QifAdapter, String>(QifAdapter.class, sqlSessionFactory,
                QifAdapterSqlmap.class.getName());
    }

    @Override
    public List<QifAdapter> select(QifAdapter filter) {
        return basicDao.select(filter, null);
    }

    @Override
    public QifAdapter selectById(String id) {
        return basicDao.selectById(id, false);
    }

    @Override
    public void update(QifAdapter qifAdapter) {
        basicDao.update(qifAdapter);
    }
}
