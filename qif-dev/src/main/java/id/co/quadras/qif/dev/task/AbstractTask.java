package id.co.quadras.qif.dev.task;

import com.google.inject.Inject;
import id.co.quadras.qif.QifTask;
import id.co.quadras.qif.dev.service.AdapterService;
import id.co.quadras.qif.exception.QifException;
import id.co.quadras.qif.model.entity.QifAdapter;
import id.co.quadras.qif.model.entity.QifAdapterProperty;

import java.util.List;

/**
 * @author irwin Timestamp : 28/05/2014 11:07
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
