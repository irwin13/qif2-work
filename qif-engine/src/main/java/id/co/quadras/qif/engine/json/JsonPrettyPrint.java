package id.co.quadras.qif.engine.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * @author irwin Timestamp : 22/07/2014 18:00
 */
public class JsonPrettyPrint {

    private final ObjectMapper objectMapper;

    public JsonPrettyPrint() {
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
