package id.co.quadras.qif.engine.connector.adapter;

import id.co.quadras.qif.engine.core.QifUtil;
import id.co.quadras.qif.model.entity.QifAdapter;
import id.co.quadras.qif.model.entity.QifAdapterProperty;

/**
 * @author irwin Timestamp : 21/05/2014 18:38
 */
public abstract class AbstractAdapter {

    protected final QifAdapter qifAdapter;

    public AbstractAdapter(QifAdapter qifAdapter) {
        this.qifAdapter = qifAdapter;
    }

    protected String getPropertyValue(String propertyKey) {
        QifAdapterProperty qifAdapterProperty = QifUtil.getAdapterProperty(qifAdapter, propertyKey);
        return (qifAdapterProperty == null) ? null : qifAdapterProperty.getPropertyValue();
    }
}
