package id.co.quadras.qif.engine;

import com.google.inject.Inject;
import id.co.quadras.qif.core.QifTask;
import id.co.quadras.qif.core.QifUtil;
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
        QifAdapterProperty qifAdapterProperty = QifUtil.getAdapterProperty(qifAdapter, propertyKey);
        return (qifAdapterProperty == null) ? null : qifAdapterProperty.getPropertyValue();
    }
}
