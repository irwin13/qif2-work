package id.co.quadras.qif.core;

import com.google.common.base.Preconditions;
import id.co.quadras.qif.core.model.entity.QifAdapter;
import id.co.quadras.qif.core.model.entity.QifAdapterProperty;
import id.co.quadras.qif.core.model.entity.QifEvent;
import id.co.quadras.qif.core.model.entity.QifEventProperty;

/**
 * @author irwin Timestamp : 12/05/2014 17:52
 */
public class QifUtil {

    public static QifEventProperty getEventProperty(QifEvent qifEvent, String key) {
        Preconditions.checkNotNull(qifEvent.getQifEventPropertyList(), "QifEventProperty List is null");
        Preconditions.checkNotNull(key, "QifEventProperty key is null");

        for (QifEventProperty property : qifEvent.getQifEventPropertyList()) {
            if (property.getPropertyKey().equals(key)) {
                return property;
            }
        }

        return null;
    }

    public static QifAdapterProperty getAdapterProperty(QifAdapter qifAdapter, String key) {
        Preconditions.checkNotNull(qifAdapter.getQifAdapterPropertyList(), "QifAdapterProperty List is null");
        Preconditions.checkNotNull(key, "QifAdapterProperty key is null");

        for (QifAdapterProperty property : qifAdapter.getQifAdapterPropertyList()) {
            if (property.getPropertyKey().equals(key)) {
                return property;
            }
        }

        return null;
    }

}
