package id.co.quadras.qif.engine.web;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import id.co.quadras.qif.core.exception.QifException;
import id.co.quadras.qif.core.model.entity.QifEvent;
import id.co.quadras.qif.core.model.entity.QifEventProperty;
import id.co.quadras.qif.core.model.vo.event.EventHttp;
import id.co.quadras.qif.core.model.vo.event.EventInterface;
import id.co.quadras.qif.core.model.vo.event.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author irwin Timestamp : 06/06/2014 19:15
 */
public class HttpEventMap {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpEventMap.class);

    private static final ConcurrentHashMap<String, String> map = new ConcurrentHashMap<String, String>();

    public static void init(List<QifEvent> qifEventList) {
        Preconditions.checkNotNull(qifEventList);
        for (QifEvent qifEvent : qifEventList) {
            if (qifEvent.getEventType().equals(EventType.INCOMING_MESSAGE) &&
                    qifEvent.getEventInterface().equals(EventInterface.HTTP)) {

                String httpPath = getPropertyValue(qifEvent, EventHttp.HTTP_PATH.getName());
                map.put(httpPath, qifEvent.getId());
                LOGGER.info("Register HTTP path {} to QifEvent {}", httpPath, qifEvent.getName());
            }
        }
    }

    public static Map<String, String> getMap() {
        return map;
    }

    private static String getPropertyValue(QifEvent qifEvent, String propertyKey) {
        List<QifEventProperty> list = qifEvent.getQifEventPropertyList();
        String result = null;
        if (list == null && list.isEmpty()) {
            throw new QifException("FATAL : empty property list on QifEvent " + qifEvent.getName());
        } else {
            for (QifEventProperty property : qifEvent.getQifEventPropertyList()) {
                if (property.getPropertyKey().equals(propertyKey)) {
                    result = property.getPropertyValue();
                }
            }
        }
        if (Strings.isNullOrEmpty(result)) {
            throw new QifException("FATAL : Missing http_path property on QifEvent " + qifEvent.getName());
        }

        return result;
    }
}
