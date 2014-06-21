package id.co.quadras.qif.ui.dao.app.imp;

import com.google.inject.Inject;
import com.irwin13.hibernate.dao.BasicHibernateDao;
import com.irwin13.winwork.basic.exception.WinWorkException;
import com.irwin13.winwork.basic.model.SearchParameter;
import com.irwin13.winwork.basic.model.SortParameter;
import com.irwin13.winwork.basic.model.entity.app.AppRole;
import com.irwin13.winwork.basic.utilities.PojoUtil;
import id.co.quadras.qif.ui.dao.app.AppRoleDao;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author irwin Timestamp : 15/04/13 11:54
 */
public class AppRoleDaoImp implements AppRoleDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppRoleDaoImp.class);

    private final SessionFactory sessionFactory;
    private final BasicHibernateDao<AppRole, String> basicDao =
            new BasicHibernateDao<AppRole, String>(AppRole.class);

    @Inject
    public AppRoleDaoImp(SessionFactory sessionFactory) {
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
    public List<AppRole> select(AppRole filter, SortParameter sortParameter) {
        return basicDao.select(filter, sortParameter);
    }

    @Override
    public List<AppRole> select(AppRole filter, SortParameter sortParameter, int start, int size) {
        return basicDao.select(filter, sortParameter, start, size);
    }

    @Override
    public long selectCount(AppRole filter) {
        return basicDao.selectCount(filter);
    }

    @Override
    public List<AppRole> selectSearch(SearchParameter searchParameter) {
        return basicDao.selectSearch(searchParameter, PojoUtil.getSearchableField(getModelClass()));
    }

    @Override
    public List<AppRole> selectSearch(SearchParameter searchParameter, int start, int size) {
        return basicDao.selectSearch(searchParameter, PojoUtil.getSearchableField(getModelClass()), start, size);
    }

    @Override
    public long selectSearchCount(SearchParameter searchParameter) {
        return basicDao.selectSearchCount(searchParameter, PojoUtil.getSearchableField(getModelClass()));
    }

    @Override
    public AppRole getById(String id, boolean init) {
        Session session = null;
        AppRole result = null;

        try {
            session = openNewSession();
            result = (AppRole) session.get(getModelClass(), id);
            if (result != null && init) {
                Hibernate.initialize(result.getAppUserList());
                Hibernate.initialize(result.getAppPermissionList());
            }
        } catch (RuntimeException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        } finally {
            closeSession(session);
        }

        return result;
    }

    @Override
    public String insert(AppRole model) {
        return basicDao.insert(model);
    }

    @Override
    public void update(AppRole model) {
        basicDao.update(model);
    }

    @Override
    public void merge(AppRole model) {
        basicDao.merge(model);
    }

    @Override
    public void delete(AppRole model) {
        basicDao.delete(model);
    }

    @Override
    public void saveOrUpdate(AppRole model) {
        basicDao.saveOrUpdate(model);
    }

    @Override
    public Class<AppRole> getModelClass() {
        return basicDao.getModelClass();
    }

    @Override
    public void batchInsert(List<AppRole> appRoles) {
        throw new WinWorkException("This method is not implemented, yet");
    }

    @Override
    public void batchUpdate(List<AppRole> appRoles) {
        throw new WinWorkException("This method is not implemented, yet");
    }

    @Override
    public void batchDelete(List<AppRole> appRoles) {
        throw new WinWorkException("This method is not implemented, yet");
    }
}
