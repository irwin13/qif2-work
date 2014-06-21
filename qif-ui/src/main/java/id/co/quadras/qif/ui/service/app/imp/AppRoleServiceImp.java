package id.co.quadras.qif.ui.service.app.imp;

import com.google.inject.Inject;
import com.irwin13.winwork.basic.model.SearchParameter;
import com.irwin13.winwork.basic.model.SortParameter;
import com.irwin13.winwork.basic.model.entity.app.AppRole;
import com.irwin13.winwork.basic.service.BasicEntityCommonService;
import id.co.quadras.qif.ui.dao.app.AppRoleDao;
import id.co.quadras.qif.ui.service.app.AppRoleService;

import java.util.List;

/**
 * @author irwin Timestamp : 15/04/13 14:39
 */
public class AppRoleServiceImp implements AppRoleService {

    private final BasicEntityCommonService commonService;
    private final AppRoleDao dao;

    @Inject
    public AppRoleServiceImp(BasicEntityCommonService commonService, AppRoleDao dao) {
        this.commonService = commonService;
        this.dao = dao;
    }

    @Override
    public List<AppRole> select(AppRole filter, SortParameter sortParameter) {
        commonService.onSelect(filter);
        return dao.select(filter, sortParameter);
    }

    @Override
    public List<AppRole> select(AppRole filter, SortParameter sortParameter, int start, int size) {
        commonService.onSelect(filter);
        return dao.select(filter, sortParameter, start, size);
    }

    @Override
    public long selectCount(AppRole filter) {
        commonService.onSelect(filter);
        return dao.selectCount(filter);
    }

    @Override
    public List<AppRole> selectSearch(SearchParameter searchParameter) {
        return dao.selectSearch(searchParameter);
    }

    @Override
    public List<AppRole> selectSearch(SearchParameter searchParameter, int start, int size) {
        return dao.selectSearch(searchParameter, start, size);
    }

    @Override
    public long selectSearchCount(String searchKeyword) {
        return dao.selectSearchCount(new SearchParameter(searchKeyword, null, null));
    }

    @Override
    public AppRole getById(String id, boolean init) {
        return dao.getById(id, init);
    }

    @Override
    public String insert(AppRole model) {
        commonService.onInsert(model);
        return dao.insert(model);
    }

    @Override
    public void update(AppRole model) {
        commonService.onUpdate(model);
        dao.merge(model);
    }

    @Override
    public void softDelete(AppRole model) {
        commonService.onSoftDelete(model);
        dao.merge(model);
    }

    @Override
    public void insertOrUpdate(AppRole model) {
        commonService.onInsertOrUpdate(model) ;
        dao.saveOrUpdate(model);
    }
}
