package id.co.quadras.qif.ui.service.config.imp;

import com.google.inject.Inject;
import com.irwin13.winwork.basic.model.SearchParameter;
import com.irwin13.winwork.basic.model.SortParameter;
import com.irwin13.winwork.basic.service.BasicEntityCommonService;
import id.co.quadras.qif.core.model.entity.QifEvent;
import id.co.quadras.qif.ui.dao.config.QifEventDao;
import id.co.quadras.qif.ui.service.config.QifEventService;

import java.util.List;

/**
 * @author irwin Timestamp : 02/07/2014 14:00
 */
public class QifEventServiceImp implements QifEventService {

    private final BasicEntityCommonService commonService;
    private final QifEventDao dao;

    @Inject
    public QifEventServiceImp(BasicEntityCommonService commonService, QifEventDao dao) {
        this.commonService = commonService;
        this.dao = dao;
    }

    @Override
    public List<QifEvent> select(QifEvent filter, SortParameter sortParameter) {
        commonService.onSelect(filter);
        return dao.select(filter, sortParameter);
    }

    @Override
    public List<QifEvent> select(QifEvent filter, SortParameter sortParameter, int start, int size) {
        commonService.onSelect(filter);
        return dao.select(filter, sortParameter, start, size);
    }

    @Override
    public long selectCount(QifEvent filter) {
        commonService.onSelect(filter);
        return dao.selectCount(filter);
    }

    @Override
    public List<QifEvent> selectSearch(SearchParameter searchParameter) {
        return dao.selectSearch(searchParameter);
    }

    @Override
    public List<QifEvent> selectSearch(SearchParameter searchParameter, int start, int size) {
        return dao.selectSearch(searchParameter, start, size);
    }

    @Override
    public long selectSearchCount(String searchKeyword) {
        return dao.selectSearchCount(new SearchParameter(searchKeyword, null, null));
    }

    @Override
    public QifEvent getById(String id, boolean init) {
        return dao.getById(id, init);
    }

    @Override
    public String insert(QifEvent model) {
        commonService.onInsert(model);
        return dao.insert(model);
    }

    @Override
    public void update(QifEvent model) {
        commonService.onUpdate(model);
        dao.merge(model);
    }

    @Override
    public void softDelete(QifEvent model) {
        commonService.onSoftDelete(model);
        dao.merge(model);
    }

    @Override
    public void insertOrUpdate(QifEvent model) {
        commonService.onInsertOrUpdate(model) ;
        dao.saveOrUpdate(model);
    }
}
