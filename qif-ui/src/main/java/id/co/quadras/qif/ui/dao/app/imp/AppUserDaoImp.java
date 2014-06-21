package id.co.quadras.qif.ui.dao.app.imp;

import com.google.inject.Inject;
import com.irwin13.hibernate.dao.BasicHibernateDao;
import com.irwin13.winwork.basic.exception.WinWorkException;
import com.irwin13.winwork.basic.model.SearchParameter;
import com.irwin13.winwork.basic.model.SortParameter;
import com.irwin13.winwork.basic.model.entity.app.AppUser;
import com.irwin13.winwork.basic.utilities.PojoUtil;
import id.co.quadras.qif.ui.dao.app.AppUserDao;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author irwin Timestamp : 15/04/13 11:54
 */
public class AppUserDaoImp implements AppUserDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppUserDaoImp.class);

    private final SessionFactory sessionFactory;
    private final BasicHibernateDao<AppUser, String> basicDao =
            new BasicHibernateDao<AppUser, String>(AppUser.class);

    @Inject
    public AppUserDaoImp(SessionFactory sessionFactory) {
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
    public List<AppUser> select(AppUser filter, SortParameter sortParameter) {
        return basicDao.select(filter, sortParameter);
    }

    @Override
    public List<AppUser> select(AppUser filter, SortParameter sortParameter, int start, int size) {
        return basicDao.select(filter, sortParameter, start, size);
    }

    @Override
    public long selectCount(AppUser filter) {
        return basicDao.selectCount(filter);
    }

    @Override
    public List<AppUser> selectSearch(SearchParameter searchParameter) {
        return basicDao.selectSearch(searchParameter, PojoUtil.getSearchableField(getModelClass()));
    }

    @Override
    public List<AppUser> selectSearch(SearchParameter searchParameter, int start, int size) {
        return basicDao.selectSearch(searchParameter, PojoUtil.getSearchableField(getModelClass()), start, size);
    }

    @Override
    public long selectSearchCount(SearchParameter searchParameter) {
        return basicDao.selectSearchCount(searchParameter, PojoUtil.getSearchableField(getModelClass()));
    }

    @Override
    public AppUser getById(String id, boolean init) {
        Session session = null;
        AppUser result = null;

        try {
            session = openNewSession();
            result = (AppUser) session.get(getModelClass(), id);
            if (result != null && init) {
                Hibernate.initialize(result.getAppRoleList());
            }
        } catch (RuntimeException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        } finally {
            closeSession(session);
        }

        return result;

    }

    @Override
    public String insert(AppUser model) {
        return basicDao.insert(model);
    }

    @Override
    public void update(AppUser model) {
        basicDao.update(model);
    }

    @Override
    public void merge(AppUser model) {
        basicDao.merge(model);
    }

    @Override
    public void delete(AppUser model) {
        basicDao.delete(model);
    }

    @Override
    public void saveOrUpdate(AppUser model) {
        basicDao.saveOrUpdate(model);
    }

    @Override
    public Class<AppUser> getModelClass() {
        return basicDao.getModelClass();
    }

    @Override
    public void batchInsert(List<AppUser> appUsers) {
        throw new WinWorkException("This method is not implemented, yet");
    }

    @Override
    public void batchUpdate(List<AppUser> appUsers) {
        throw new WinWorkException("This method is not implemented, yet");
    }

    @Override
    public void batchDelete(List<AppUser> appUsers) {
        throw new WinWorkException("This method is not implemented, yet");
    }
}
