package id.co.quadras.qif.engine;

import com.google.inject.Inject;
import id.co.quadras.qif.core.QifTask;
import id.co.quadras.qif.core.exception.QifException;
import id.co.quadras.qif.core.model.entity.QifAdapter;
import id.co.quadras.qif.core.model.entity.QifAdapterProperty;
import id.co.quadras.qif.engine.service.AdapterService;

import java.util.List;

/**
 * @author irwin Timestamp : 11/06/2014 21:51
 */
public abstract class AbstractTask extends QifTask {

    @Inject
    private AdapterService adapterService;

    protected QifAdapter getAdapter(String name) {
        return adapterService.selectByName(name);
    }

    protected String getPropertyValue(QifAdapter qifAdapter, String propertyKey) {
        List<QifAdapterProperty> list = qifAdapter.getQifAdapterPropertyList();
        String result = null;
        if (list == null && list.isEmpty()) {
            throw new QifException("FATAL : empty property list on QifAdapter " + qifAdapter.getName());
        } else {
            for (QifAdapterProperty property : qifAdapter.getQifAdapterPropertyList()) {
                if (property.getPropertyKey().equals(propertyKey)) {
                    result = property.getPropertyValue();
                }
            }
        }
        return result;
    }
}
