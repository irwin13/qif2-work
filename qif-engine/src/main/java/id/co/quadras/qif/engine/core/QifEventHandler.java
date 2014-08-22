package id.co.quadras.qif.engine.core;

import id.co.quadras.qif.model.entity.QifEvent;
import id.co.quadras.qif.model.entity.QifEventProperty;

/**
 * @author irwin Timestamp : 06/08/2014 12:10
 */
public abstract class QifEventHandler {

    protected final QifEvent qifEvent;
    protected final Object eventMessage;

    protected QifEventHandler(QifEvent qifEvent, Object eventMessage) {
        this.qifEvent = qifEvent;
        this.eventMessage = eventMessage;
    }

    protected String getPropertyValue(QifEvent qifEvent, String propertyKey) {
        QifEventProperty qifEventProperty = QifUtil.getEventProperty(qifEvent, propertyKey);
        return (qifEventProperty == null) ? null : qifEventProperty.getPropertyValue();
    }
}
