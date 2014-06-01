package id.co.quadras.qif.dev.guice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.inject.Provider;

/**
 * @author irwin Timestamp : 01/06/2014 23:45
 */
public class JsonMapperProvider implements Provider<ObjectMapper> {

    @Override
    public ObjectMapper get() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return objectMapper;
    }

}