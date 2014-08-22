package id.co.quadras.qif.engine.bundle;

import com.google.inject.AbstractModule;
import id.co.quadras.qif.engine.config.QifConfig;
import id.co.quadras.qif.engine.guice.QifGuice;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.util.List;

/**
 * @author irwin Timestamp : 22/08/2014 20:51
 */
public class GuiceBundle implements ConfiguredBundle<QifConfig> {

    private final List<AbstractModule> moduleList;

    public GuiceBundle(List<AbstractModule> moduleList) {
        this.moduleList = moduleList;
    }

    @Override
    public void run(QifConfig qifConfig, Environment environment) throws Exception {
        QifGuice.initEngine(qifConfig, moduleList);
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) {
    }
}
