package id.co.quadras.qif.ui.dao.app.imp;

import com.google.inject.Inject;
import com.irwin13.hibernate.dao.BasicHibernateDao;
import com.irwin13.winwork.basic.exception.WinWorkException;
import com.irwin13.winwork.basic.model.SearchParameter;
import com.irwin13.winwork.basic.model.SortParameter;
import com.irwin13.winwork.basic.model.entity.app.AppSetting;
import com.irwin13.winwork.basic.utilities.PojoUtil;
import id.co.quadras.qif.ui.dao.app.AppSettingDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * @author irwin Timestamp : 15/04/13 11:54
 */
public class AppSettingDaoImp implements AppSettingDao {

    private final SessionFactory sessionFactory;
    private final BasicHibernateDao<AppSetting, String> basicDao =
            new BasicHibernateDao<AppSetting, String>(AppSetting.class);

    @Inject
    public AppSettingDaoImp(SessionFactory sessionFactory) {
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
    public List<AppSetting> select(AppSetting filter, SortParameter sortParameter) {
        return basicDao.select(filter, sortParameter);
    }

    @Override
    public List<AppSetting> select(AppSetting filter, SortParameter sortParameter, int start, int size) {
        return basicDao.select(filter, sortParameter, start, size);
    }

    @Override
    public long selectCount(AppSetting filter) {
        return basicDao.selectCount(filter);
    }

    @Override
    public List<AppSetting> selectSearch(SearchParameter searchParameter) {
        return basicDao.selectSearch(searchParameter, PojoUtil.getSearchableField(getModelClass()));
    }

    @Override
    public List<AppSetting> selectSearch(SearchParameter searchParameter, int start, int size) {
        return basicDao.selectSearch(searchParameter, PojoUtil.getSearchableField(getModelClass()), start, size);
    }

    @Override
    public long selectSearchCount(SearchParameter searchParameter) {
        return basicDao.selectSearchCount(searchParameter, PojoUtil.getSearchableField(getModelClass()));
    }

    @Override
    public AppSetting getById(String id, boolean init) {
        return basicDao.getById(id, init);
    }

    @Override
    public String insert(AppSetting model) {
        return basicDao.insert(model);
    }

    @Override
    public void update(AppSetting model) {
        basicDao.update(model);
    }

    @Override
    public void merge(AppSetting model) {
        basicDao.merge(model);
    }

    @Override
    public void delete(AppSetting model) {
        basicDao.delete(model);
    }

    @Override
    public void saveOrUpdate(AppSetting model) {
        basicDao.saveOrUpdate(model);
    }

    @Override
    public Class<AppSetting> getModelClass() {
        return basicDao.getModelClass();
    }

    @Override
    public void batchInsert(List<AppSetting> appSettings) {
        throw new WinWorkException("This method is not implemented, yet");
    }

    @Override
    public void batchUpdate(List<AppSetting> appSettings) {
        throw new WinWorkException("This method is not implemented, yet");
    }

    @Override
    public void batchDelete(List<AppSetting> appSettings) {
        throw new WinWorkException("This method is not implemented, yet");
    }
}
