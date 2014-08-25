package id.co.quadras.qif.engine;

import com.google.inject.Singleton;
import com.irwin13.winwork.basic.config.WinWorkConfig;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author irwin Timestamp : 25/08/2014 16:47
 */
@Singleton
public class QifProperties extends WinWorkConfig {

    private static final String PROPERTIES_FILE = "qif.properties";
    private static final Logger LOGGER = LoggerFactory.getLogger(QifProperties.class);

    private Configuration configuration;

    @Override
    public Configuration getConfiguration() {
        if (configuration == null) {
            try {
                configuration = new PropertiesConfiguration(PROPERTIES_FILE);
            } catch (ConfigurationException e) {
                LOGGER.error(e.getLocalizedMessage(), e);
                throw new RuntimeException(e);
            }
        }
        return configuration;
    }
}
