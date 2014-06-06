package id.co.quadras.qif.engine;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.irwin13.winwork.basic.config.WinWorkConfig;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.DefaultConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author irwin Timestamp : 17/05/2014 21:10
 */
public class QifConfig extends WinWorkConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(QifConfig.class);

    private Configuration configuration;

    @Inject
    public QifConfig(@Named("configFile") String file) {
        try {
            DefaultConfigurationBuilder configurationBuilder = new DefaultConfigurationBuilder(file);
            configuration = configurationBuilder.getConfiguration();
        } catch (ConfigurationException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }
}
