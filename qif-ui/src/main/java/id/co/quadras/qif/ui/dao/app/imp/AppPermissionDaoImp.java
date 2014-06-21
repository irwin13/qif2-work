package id.co.quadras.qif.ui.dao.app.imp;

import com.google.inject.Inject;
import com.irwin13.hibernate.dao.BasicHibernateDao;
import com.irwin13.winwork.basic.exception.WinWorkException;
import com.irwin13.winwork.basic.model.SearchParameter;
import com.irwin13.winwork.basic.model.SortParameter;
import com.irwin13.winwork.basic.model.entity.app.AppPermission;
import com.irwin13.winwork.basic.utilities.PojoUtil;
import id.co.quadras.qif.ui.dao.app.AppPermissionDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 * @author irwin Timestamp : 15/04/13 11:54
 */
public class AppPermissionDaoImp implements AppPermissionDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppPermissionDaoImp.class);

    private final SessionFactory sessionFactory;
    private final BasicHibernateDao<AppPermission, String> basicDao =
            new BasicHibernateDao<AppPermission, String>(AppPermission.class);

    @Inject
    public AppPermissionDaoImp(SessionFactory sessionFactory) {
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
    public List<AppPermission> select(AppPermission filter, SortParameter sortParameter) {
        return basicDao.select(filter, sortParameter);
    }

    @Override
    public List<AppPermission> select(AppPermission filter, SortParameter sortParameter, int start, int size) {
        return basicDao.select(filter, sortParameter, start, size);
    }

    @Override
    public long selectCount(AppPermission filter) {
        return basicDao.selectCount(filter);
    }

    @Override
    public List<AppPermission> selectSearch(SearchParameter searchParameter) {
        return basicDao.selectSearch(searchParameter, PojoUtil.getSearchableField(getModelClass()));
    }

    @Override
    public List<AppPermission> selectSearch(SearchParameter searchParameter, int start, int size) {
        return basicDao.selectSearch(searchParameter, PojoUtil.getSearchableField(getModelClass()), start, size);
    }

    @Override
    public long selectSearchCount(SearchParameter searchParameter) {
        return basicDao.selectSearchCount(searchParameter, PojoUtil.getSearchableField(getModelClass()));
    }

    @Override
    public AppPermission getById(String id, boolean init) {
        return basicDao.getById(id, init);
    }

    @Override
    public String insert(AppPermission model) {
        return basicDao.insert(model);
    }

    @Override
    public void update(AppPermission model) {
        basicDao.update(model);
    }

    public void merge(AppPermission model) {
        basicDao.merge(model);
    }

    @Override
    public void delete(AppPermission model) {
        basicDao.delete(model);
    }

    @Override
    public void saveOrUpdate(AppPermission model) {
        basicDao.saveOrUpdate(model);
    }

    @Override
    public Class<AppPermission> getModelClass() {
        return basicDao.getModelClass();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AppPermission> getChildList(AppPermission parent) {
        List<AppPermission> result = null;
        Session session = null;

        try {
            session = openNewSession();

            StringBuilder stringQuery = new StringBuilder();
            stringQuery.append("FROM ");
            stringQuery.append(AppPermission.class.getName());
            stringQuery.append(" menu ");
            stringQuery.append("WHERE menu.parentMenu.id = :parentMenu AND menu.active = :active ");
            stringQuery.append("ORDER BY menu.name, ");
            stringQuery.append("menu.menuOrder, ");
            stringQuery.append("menu.httpPath, ");
            stringQuery.append("menu.httpMethod ");

            Query query = session.createQuery(stringQuery.toString());

            query.setString("parentMenu", parent.getId());
            query.setBoolean("active", Boolean.TRUE);
            result = (List<AppPermission>) query.list();
        } catch (RuntimeException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        } finally {
            if (session != null) session.close();
        }

        return (result != null) ? result : Collections.EMPTY_LIST;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AppPermission> getNullParent() {
        List<AppPermission> result = null;
        Session session = null;

        try {
            session = openNewSession();

            StringBuilder stringQuery = new StringBuilder();
            stringQuery.append("FROM ");
            stringQuery.append(AppPermission.class.getName());
            stringQuery.append(" menu ");
            stringQuery.append("WHERE menu.parentMenu IS NULL AND menu.active = :active ");
            stringQuery.append("ORDER BY menu.name, ");
            stringQuery.append("menu.menuOrder, ");
            stringQuery.append("menu.httpPath, ");
            stringQuery.append("menu.httpMethod ");

            Query query = session.createQuery(stringQuery.toString());
            query.setBoolean("active", Boolean.TRUE);
            result = (List<AppPermission>) query.list();
        } catch (RuntimeException e) {
            LOGGER.error(e.getLocalizedMessage(), e.getCause());
        } finally {
            if (session != null) session.close();
        }

        return (result != null) ? result : Collections.EMPTY_LIST;
    }

    @Override
    public void batchInsert(List<AppPermission> appPermissions) {
        throw new WinWorkException("This method is not implemented, yet");
    }

    @Override
    public void batchUpdate(List<AppPermission> appPermissions) {
        throw new WinWorkException("This method is not implemented, yet");
    }

    @Override
    public void batchDelete(List<AppPermission> appPermissions) {
        throw new WinWorkException("This method is not implemented, yet");
    }
}
