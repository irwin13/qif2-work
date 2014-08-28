package id.co.quadras.qif.ui.dao.config.imp;

import id.co.quadras.qif.model.entity.QifEvent;
import id.co.quadras.qif.ui.dao.config.QifEventDao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;
import com.irwin13.hibernate.dao.BasicHibernateDao;
import com.irwin13.winwork.basic.exception.WinWorkException;
import com.irwin13.winwork.basic.model.SearchParameter;
import com.irwin13.winwork.basic.model.SortParameter;
import com.irwin13.winwork.basic.utilities.PojoUtil;

/**
 * @author irwin Timestamp : 02/07/2014 13:57
 */
public class QifEventDaoImp implements QifEventDao {

    private final SessionFactory sessionFactory;
    private final BasicHibernateDao<QifEvent, String> basicDao =
            new BasicHibernateDao<QifEvent, String>(QifEvent.class);

    @Inject
    public QifEventDaoImp(SessionFactory sessionFactory) {
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
    public List<QifEvent> select(QifEvent filter, SortParameter sortParameter) {
        return basicDao.select(filter, sortParameter);
    }

    @Override
    public List<QifEvent> select(QifEvent filter, SortParameter sortParameter, int start, int size) {
        return basicDao.select(filter, sortParameter, start, size);
    }

    @Override
    public long selectCount(QifEvent filter) {
        return basicDao.selectCount(filter);
    }

    @Override
    public List<QifEvent> selectSearch(SearchParameter searchParameter) {
        return basicDao.selectSearch(searchParameter, PojoUtil.getSearchableField(getModelClass()));
    }

    @Override
    public List<QifEvent> selectSearch(SearchParameter searchParameter, int start, int size) {
        return basicDao.selectSearch(searchParameter, PojoUtil.getSearchableField(getModelClass()), start, size);
    }

    @Override
    public long selectSearchCount(SearchParameter searchParameter) {
        return basicDao.selectSearchCount(searchParameter, PojoUtil.getSearchableField(getModelClass()));
    }

    @Override
    public QifEvent getById(String id, boolean init) {
        return basicDao.getById(id, init);
    }

    @Override
    public String insert(QifEvent model) {
        return basicDao.insert(model);
    }

    @Override
    public void update(QifEvent model) {
        basicDao.update(model);
    }

    @Override
    public void merge(QifEvent model) {
        basicDao.merge(model);
    }

    @Override
    public void delete(QifEvent model) {
        basicDao.delete(model);
    }

    @Override
    public void saveOrUpdate(QifEvent model) {
        basicDao.saveOrUpdate(model);
    }

    @Override
    public Class<QifEvent> getModelClass() {
        return basicDao.getModelClass();
    }

    @Override
    public void batchInsert(List<QifEvent> appOptions) {
        throw new WinWorkException("Method not implemented");
    }

    @Override
    public void batchUpdate(List<QifEvent> appOptions) {
        throw new WinWorkException("Method not implemented");
    }

    @Override
    public void batchDelete(List<QifEvent> appOptions) {
        throw new WinWorkException("Method not implemented");
    }
}
