package id.co.quadras.qif.engine.service.imp;

import com.google.inject.Inject;
import id.co.quadras.qif.core.exception.QifException;
import id.co.quadras.qif.core.model.entity.QifAdapter;
import id.co.quadras.qif.core.model.entity.QifAdapterProperty;
import id.co.quadras.qif.engine.dao.AdapterDao;
import id.co.quadras.qif.engine.dao.AdapterPropertyDao;
import id.co.quadras.qif.engine.service.AdapterService;

import java.util.List;

/**
 * @author irwin Timestamp : 17/05/2014 21:28
 */
public class AdapterServiceImp implements AdapterService {

    private final AdapterDao adapterDao;
    private final AdapterPropertyDao adapterPropertyDao;

    @Inject
    public AdapterServiceImp(AdapterDao adapterDao, AdapterPropertyDao adapterPropertyDao) {
        this.adapterDao = adapterDao;
        this.adapterPropertyDao = adapterPropertyDao;
    }

    @Override
    public QifAdapter selectByName(String name) {
        QifAdapter qifAdapter;
        QifAdapter filter = new QifAdapter();
        filter.setName(name);
        List<QifAdapter> list = adapterDao.select(filter);
        if (list.isEmpty()) {
            throw new QifException("FATAL : Missing QifAdapter with name " + name + " in database");
        } else {
            qifAdapter = list.get(0);
            List<QifAdapterProperty> propertyList = adapterPropertyDao.selectByAdapter(qifAdapter.getId());
            qifAdapter.setQifAdapterPropertyList(propertyList);
        }
        return qifAdapter;
    }

    @Override
    public QifAdapter selectById(String id) {
        QifAdapter qifAdapter = adapterDao.selectById(id);
        if (qifAdapter == null) {
            throw new QifException("FATAL : Missing QifAdapter with id " + id + " in database");
        } else {
            List<QifAdapterProperty> propertyList = adapterPropertyDao.selectByAdapter(id);
            qifAdapter.setQifAdapterPropertyList(propertyList);
        }
        return qifAdapter;
    }
}
