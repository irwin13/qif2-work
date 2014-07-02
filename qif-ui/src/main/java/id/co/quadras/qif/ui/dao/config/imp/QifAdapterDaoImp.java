package id.co.quadras.qif.ui.dao.config.imp;

import com.google.inject.Inject;
import com.irwin13.hibernate.dao.BasicHibernateDao;
import com.irwin13.winwork.basic.exception.WinWorkException;
import com.irwin13.winwork.basic.model.SearchParameter;
import com.irwin13.winwork.basic.model.SortParameter;
import com.irwin13.winwork.basic.utilities.PojoUtil;
import id.co.quadras.qif.core.model.entity.QifAdapter;
import id.co.quadras.qif.ui.dao.config.QifAdapterDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * @author irwin Timestamp : 02/07/2014 13:57
 */
public class QifAdapterDaoImp implements QifAdapterDao {

    private final SessionFactory sessionFactory;
    private final BasicHibernateDao<QifAdapter, String> basicDao =
            new BasicHibernateDao<QifAdapter, String>(QifAdapter.class);

    @Inject
    public QifAdapterDaoImp(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        basicDao.setSessionFactory(sessionFactory);
    }

    public Session openNewSession() {
        return basicDao.openNewSession();
    }

    public void closeSession(Session session) {
        basicDao.closeSession(session);
    }

    @Override
    public List<QifAdapter> select(QifAdapter filter, SortParameter sortParameter) {
        return basicDao.select(filter, sortParameter);
    }

    @Override
    public List<QifAdapter> select(QifAdapter filter, SortParameter sortParameter, int start, int size) {
        return basicDao.select(filter, sortParameter, start, size);
    }

    @Override
    public long selectCount(QifAdapter filter) {
        return basicDao.selectCount(filter);
    }

    @Override
    public List<QifAdapter> selectSearch(SearchParameter searchParameter) {
        return basicDao.selectSearch(searchParameter, PojoUtil.getSearchableField(getModelClass()));
    }

    @Override
    public List<QifAdapter> selectSearch(SearchParameter searchParameter, int start, int size) {
        return basicDao.selectSearch(searchParameter, PojoUtil.getSearchableField(getModelClass()), start, size);
    }

    @Override
    public long selectSearchCount(SearchParameter searchParameter) {
        return basicDao.selectSearchCount(searchParameter, PojoUtil.getSearchableField(getModelClass()));
    }

    @Override
    public QifAdapter getById(String id, boolean init) {
        return basicDao.getById(id, init);
    }

    @Override
    public String insert(QifAdapter model) {
        return basicDao.insert(model);
    }

    @Override
    public void update(QifAdapter model) {
        basicDao.update(model);
    }

    @Override
    public void merge(QifAdapter model) {
        basicDao.merge(model);
    }

    @Override
    public void delete(QifAdapter model) {
        basicDao.delete(model);
    }

    @Override
    public void saveOrUpdate(QifAdapter model) {
        basicDao.saveOrUpdate(model);
    }

    @Override
    public Class<QifAdapter> getModelClass() {
        return basicDao.getModelClass();
    }

    @Override
    public void batchInsert(List<QifAdapter> appOptions) {
        throw new WinWorkException("Method not implemented");
    }

    @Override
    public void batchUpdate(List<QifAdapter> appOptions) {
        throw new WinWorkException("Method not implemented");
    }

    @Override
    public void batchDelete(List<QifAdapter> appOptions) {
        throw new WinWorkException("Method not implemented");
    }
}
