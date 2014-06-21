package id.co.quadras.qif.ui.dao.app.imp;

import com.google.inject.Inject;
import com.irwin13.hibernate.dao.BasicHibernateDao;
import com.irwin13.winwork.basic.exception.WinWorkException;
import com.irwin13.winwork.basic.model.SearchParameter;
import com.irwin13.winwork.basic.model.SortParameter;
import com.irwin13.winwork.basic.model.entity.app.AppOption;
import com.irwin13.winwork.basic.utilities.PojoUtil;
import id.co.quadras.qif.ui.dao.app.AppOptionDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * @author irwin Timestamp : 12/04/13 17:29
 */
public class AppOptionDaoImp implements AppOptionDao {

    private final SessionFactory sessionFactory;
    private final BasicHibernateDao<AppOption, String> basicDao =
            new BasicHibernateDao<AppOption, String>(AppOption.class);

    @Inject
    public AppOptionDaoImp(SessionFactory sessionFactory) {
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
    public List<AppOption> select(AppOption filter, SortParameter sortParameter) {
        return basicDao.select(filter, sortParameter);
    }

    @Override
    public List<AppOption> select(AppOption filter, SortParameter sortParameter, int start, int size) {
        return basicDao.select(filter, sortParameter, start, size);
    }

    @Override
    public long selectCount(AppOption filter) {
        return basicDao.selectCount(filter);
    }

    @Override
    public List<AppOption> selectSearch(SearchParameter searchParameter) {
        return basicDao.selectSearch(searchParameter, PojoUtil.getSearchableField(getModelClass()));
    }

    @Override
    public List<AppOption> selectSearch(SearchParameter searchParameter, int start, int size) {
        return basicDao.selectSearch(searchParameter, PojoUtil.getSearchableField(getModelClass()), start, size);
    }

    @Override
    public long selectSearchCount(SearchParameter searchParameter) {
        return basicDao.selectSearchCount(searchParameter, PojoUtil.getSearchableField(getModelClass()));
    }

    @Override
    public AppOption getById(String id, boolean init) {
        return basicDao.getById(id, init);
    }

    @Override
    public String insert(AppOption model) {
        return basicDao.insert(model);
    }

    @Override
    public void update(AppOption model) {
        basicDao.update(model);
    }

    @Override
    public void merge(AppOption model) {
        basicDao.merge(model);
    }

    @Override
    public void delete(AppOption model) {
        basicDao.delete(model);
    }

    @Override
    public void saveOrUpdate(AppOption model) {
        basicDao.saveOrUpdate(model);
    }

    @Override
    public Class<AppOption> getModelClass() {
        return basicDao.getModelClass();
    }

    @Override
    public void batchInsert(List<AppOption> appOptions) {
        throw new WinWorkException("Method not implemented");
    }

    @Override
    public void batchUpdate(List<AppOption> appOptions) {
        throw new WinWorkException("Method not implemented");
    }

    @Override
    public void batchDelete(List<AppOption> appOptions) {
        throw new WinWorkException("Method not implemented");
    }
}