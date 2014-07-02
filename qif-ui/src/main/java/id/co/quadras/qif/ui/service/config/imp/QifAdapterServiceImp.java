package id.co.quadras.qif.ui.service.config.imp;

import com.google.inject.Inject;
import com.irwin13.winwork.basic.model.SearchParameter;
import com.irwin13.winwork.basic.model.SortParameter;
import com.irwin13.winwork.basic.service.BasicEntityCommonService;
import id.co.quadras.qif.core.model.entity.QifAdapter;
import id.co.quadras.qif.ui.dao.config.QifAdapterDao;
import id.co.quadras.qif.ui.service.config.QifAdapterService;

import java.util.List;

/**
 * @author irwin Timestamp : 02/07/2014 14:00
 */
public class QifAdapterServiceImp implements QifAdapterService {

    private final BasicEntityCommonService commonService;
    private final QifAdapterDao dao;

    @Inject
    public QifAdapterServiceImp(BasicEntityCommonService commonService, QifAdapterDao dao) {
        this.commonService = commonService;
        this.dao = dao;
    }

    @Override
    public List<QifAdapter> select(QifAdapter filter, SortParameter sortParameter) {
        commonService.onSelect(filter);
        return dao.select(filter, sortParameter);
    }

    @Override
    public List<QifAdapter> select(QifAdapter filter, SortParameter sortParameter, int start, int size) {
        commonService.onSelect(filter);
        return dao.select(filter, sortParameter, start, size);
    }

    @Override
    public long selectCount(QifAdapter filter) {
        commonService.onSelect(filter);
        return dao.selectCount(filter);
    }

    @Override
    public List<QifAdapter> selectSearch(SearchParameter searchParameter) {
        return dao.selectSearch(searchParameter);
    }

    @Override
    public List<QifAdapter> selectSearch(SearchParameter searchParameter, int start, int size) {
        return dao.selectSearch(searchParameter, start, size);
    }

    @Override
    public long selectSearchCount(String searchKeyword) {
        return dao.selectSearchCount(new SearchParameter(searchKeyword, null, null));
    }

    @Override
    public QifAdapter getById(String id, boolean init) {
        return dao.getById(id, init);
    }

    @Override
    public String insert(QifAdapter model) {
        commonService.onInsert(model);
        return dao.insert(model);
    }

    @Override
    public void update(QifAdapter model) {
        commonService.onUpdate(model);
        dao.merge(model);
    }

    @Override
    public void softDelete(QifAdapter model) {
        commonService.onSoftDelete(model);
        dao.merge(model);
    }

    @Override
    public void insertOrUpdate(QifAdapter model) {
        commonService.onInsertOrUpdate(model) ;
        dao.saveOrUpdate(model);
    }
}
