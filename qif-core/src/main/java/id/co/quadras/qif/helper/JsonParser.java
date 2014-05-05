package id.co.quadras.qif.helper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.irwin13.winwork.basic.annotations.MDCLog;
import com.irwin13.winwork.basic.utilities.StringCompressor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author irwin Timestamp : 05/05/2014 19:40
 */
public final class JsonParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonParser.class);

    private final ObjectMapper objectMapper;

    @Inject
    public JsonParser(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @MDCLog
    public <T> T parseToObject(boolean compressed, String jsonString, Class<T> clazz) throws IOException {
        T result;
        String message;
        if (compressed) {
            message = StringCompressor.decompress(jsonString);
        } else {
            message = jsonString;
        }
        LOGGER.trace("parseToObject class = {}", clazz.getName());
        LOGGER.trace("parseToObject json message = {}", message);
        result = objectMapper.readValue(message, clazz);
        return result;
    }

    @MDCLog
    public <T> T parseToObject(boolean compressed, String jsonString, TypeReference<T> typeReference) throws IOException {
        T result;
        String message;

        if (compressed) {
            message = StringCompressor.decompress(jsonString);
        } else {
            message = jsonString;
        }
        LOGGER.trace("parseToObject typeReference = {}", typeReference.getType());
        LOGGER.trace("parseToObject json message = {}", message);
        result = objectMapper.readValue(message, typeReference);
        return result;
    }

    @MDCLog
    public String parseToString(boolean compressed, Object object) throws IOException {
        String jsonString = objectMapper.writeValueAsString(object);
        LOGGER.trace("parseToString json message = {}", jsonString);
        if (compressed) {
            return StringCompressor.compress(jsonString);
        } else {
            return jsonString;
        }
    }

}
