package id.co.quadras.qif.ui.service.app.imp;

import com.google.inject.Inject;
import com.irwin13.winwork.basic.exception.WinWorkException;
import com.irwin13.winwork.basic.model.SearchParameter;
import com.irwin13.winwork.basic.model.SortParameter;
import com.irwin13.winwork.basic.model.entity.app.AppSetting;
import com.irwin13.winwork.basic.service.BasicEntityCommonService;
import id.co.quadras.qif.ui.dao.app.AppSettingDao;
import id.co.quadras.qif.ui.service.app.AppSettingService;

import java.util.List;

/**
 * @author irwin Timestamp : 15/04/13 14:39
 *
 * Edit By : Nova, 02/05/13
 * Add implement method getByCategory
 *
 */
public class AppSettingServiceImp implements AppSettingService {

    private final BasicEntityCommonService commonService;
    private final AppSettingDao dao;

    @Inject
    public AppSettingServiceImp(BasicEntityCommonService commonService, AppSettingDao dao) {
        this.commonService = commonService;
        this.dao = dao;
    }

    @Override
    public List<AppSetting> select(AppSetting filter, SortParameter sortParameter) {
        commonService.onSelect(filter);
        return dao.select(filter, sortParameter);
    }

    @Override
    public List<AppSetting> select(AppSetting filter, SortParameter sortParameter, int start, int size) {
        commonService.onSelect(filter);
        return dao.select(filter, sortParameter, start, size);
    }

    @Override
    public long selectCount(AppSetting filter) {
        commonService.onSelect(filter);
        return dao.selectCount(filter);
    }

    @Override
    public List<AppSetting> selectSearch(SearchParameter searchParameter) {
        return dao.selectSearch(searchParameter);
    }

    @Override
    public List<AppSetting> selectSearch(SearchParameter searchParameter, int start, int size) {
        return dao.selectSearch(searchParameter, start, size);
    }

    @Override
    public long selectSearchCount(String searchKeyword) {
        return dao.selectSearchCount(new SearchParameter(searchKeyword, null, null));
    }

    @Override
    public AppSetting getById(String id, boolean init) {
        return dao.getById(id, init);
    }

    @Override
    public String insert(AppSetting model) {
        commonService.onInsert(model);
        return dao.insert(model);
    }

    @Override
    public void update(AppSetting model) {
        commonService.onUpdate(model);
        dao.merge(model);
    }

    @Override
    public void softDelete(AppSetting model) {
        commonService.onSoftDelete(model);
        dao.merge(model);
    }

    @Override
    public void insertOrUpdate(AppSetting model) {
        commonService.onInsertOrUpdate(model) ;
        dao.saveOrUpdate(model);
    }

    @Override
    public AppSetting getByCode(String code) {
        AppSetting appSetting = null;

        AppSetting filter = new AppSetting();
        filter.setActive(Boolean.TRUE);
        filter.setCode(code);

        List<AppSetting> list = select(filter, null);
        if (list != null && !list.isEmpty()) {
            appSetting = list.get(0);
        }

        return appSetting;
    }

    @Override
    public List<AppSetting> getByCategory(String category) {

        AppSetting filter = new AppSetting();
        filter.setActive(Boolean.TRUE);
        filter.setSettingCategory(category);

        List<AppSetting> listAppSetting = select(filter, null);

        return listAppSetting;
    }

    @Override
    public String getSettingStringValue(String code) {
        String result;
        AppSetting appSetting = getByCode(code);
        if (appSetting == null) {
            throw new WinWorkException("FATAL : AppSetting with code = '" + code +"' not found in database");
        }
        result = appSetting.getStringValue();
        return result;
    }

    @Override
    public int getSettingIntValue(String code) {
        return Integer.valueOf(getSettingStringValue(code));
    }
}
