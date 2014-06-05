package id.co.quadras.qif.adapter;

import id.co.quadras.qif.core.exception.QifException;
import id.co.quadras.qif.core.model.entity.QifAdapter;
import id.co.quadras.qif.core.model.entity.QifAdapterProperty;

import java.util.List;

/**
 * @author irwin Timestamp : 21/05/2014 18:38
 */
public abstract class AbstractAdapter {

    protected final QifAdapter qifAdapter;

    public AbstractAdapter(QifAdapter qifAdapter) {
        this.qifAdapter = qifAdapter;
    }

    protected String getPropertyValue(String propertyKey) {
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
