package id.co.quadras.qif.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.irwin13.winwork.basic.utilities.StringCompressor;
import id.co.quadras.qif.core.model.entity.QifAdapter;
import id.co.quadras.qif.core.model.entity.QifAdapterProperty;
import id.co.quadras.qif.core.model.entity.QifEvent;
import id.co.quadras.qif.core.model.entity.QifEventProperty;
import id.co.quadras.qif.core.model.vo.message.QifMessageType;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author irwin Timestamp : 12/05/2014 17:52
 */
public class QifUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(QifUtil.class);

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

    public static String convertObjectContentToString(Object content, QifMessageType messageType, ObjectMapper objectMapper) {
        String result = null;

        if (QifMessageType.STRING.equals(messageType)) {
            result = StringCompressor.compress((String) content);
        } else if (QifMessageType.OBJECT.equals(messageType)) {
            try {
                 result = StringCompressor.compress(objectMapper.writeValueAsString(content));
            } catch (IOException e) {
                LOGGER.error(e.getLocalizedMessage(), e);
                result = convertThrowableToJson(e);
            }
        } else if (QifMessageType.BINARY.equals(messageType)) {
            result = Base64.encodeBase64String((byte[]) content);
        }

        return result;
    }

    public static String convertThrowableToJson(Throwable throwable) {
        StringBuilder error = new StringBuilder();
        error.append("{");
        error.append("\"errorMessage\":");
        error.append("\"");
        error.append(ExceptionUtils.getStackTrace(throwable));
        error.append("\"");
        error.append("}");
        return error.toString();
    }
}
