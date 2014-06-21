package id.co.quadras.qif.ui.service.app.imp;

import com.google.inject.Inject;
import com.irwin13.winwork.basic.model.SearchParameter;
import com.irwin13.winwork.basic.model.SortParameter;
import com.irwin13.winwork.basic.model.entity.app.AppOption;
import com.irwin13.winwork.basic.service.BasicEntityCommonService;
import id.co.quadras.qif.ui.dao.app.AppOptionDao;
import id.co.quadras.qif.ui.service.app.AppOptionService;

import java.util.List;

/**
 * @author irwin Timestamp : 12/04/13 17:33
 */
public class AppOptionServiceImp implements AppOptionService {

    private final BasicEntityCommonService commonService;
    private final AppOptionDao dao;

    @Inject
    public AppOptionServiceImp(BasicEntityCommonService commonService, AppOptionDao dao) {
        this.commonService = commonService;
        this.dao = dao;
    }

    @Override
    public List<AppOption> select(AppOption filter, SortParameter sortParameter) {
        commonService.onSelect(filter);
        return dao.select(filter, sortParameter);
    }

    @Override
    public List<AppOption> select(AppOption filter, SortParameter sortParameter, int start, int size) {
        commonService.onSelect(filter);
        return dao.select(filter, sortParameter, start, size);
    }

    @Override
    public long selectCount(AppOption filter) {
        commonService.onSelect(filter);
        return dao.selectCount(filter);
    }

    @Override
    public List<AppOption> selectSearch(SearchParameter searchParameter) {
        return dao.selectSearch(searchParameter);
    }

    @Override
    public List<AppOption> selectSearch(SearchParameter searchParameter, int start, int size) {
        return dao.selectSearch(searchParameter, start, size);
    }

    @Override
    public long selectSearchCount(String searchKeyword) {
        return dao.selectSearchCount(new SearchParameter(searchKeyword, null, null));
    }

    @Override
    public AppOption getById(String id, boolean init) {
        return dao.getById(id, init);
    }

    @Override
    public String insert(AppOption model) {
        commonService.onInsert(model);
        return dao.insert(model);
    }

    @Override
    public void update(AppOption model) {
        commonService.onUpdate(model);
        dao.merge(model);
    }

    @Override
    public void softDelete(AppOption model) {
        commonService.onSoftDelete(model);
        dao.merge(model);
    }

    @Override
    public void insertOrUpdate(AppOption model) {
        commonService.onInsertOrUpdate(model) ;
        dao.saveOrUpdate(model);
    }

    @Override
    public List<AppOption> selectByCategory(String category) {
        List<AppOption> result;
        AppOption filter = new AppOption();
        filter.setActive(Boolean.TRUE);
        filter.setOptionCategory(category);
        result = select(filter, new SortParameter("name", SortParameter.ASC));
        return result;
    }
}
