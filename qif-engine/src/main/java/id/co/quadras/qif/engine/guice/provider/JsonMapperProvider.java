package id.co.quadras.qif.engine.guice.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * @author irwin Timestamp : 01/06/2014 23:45
 */
@Singleton
public class JsonMapperProvider implements Provider<ObjectMapper> {

    @Override
    public ObjectMapper get() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return objectMapper;
    }

}
