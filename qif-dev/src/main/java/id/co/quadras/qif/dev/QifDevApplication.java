package id.co.quadras.qif.dev;

import com.google.inject.AbstractModule;
import id.co.quadras.qif.engine.QifEngineApplication;
import id.co.quadras.qif.engine.config.QifConfig;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.util.List;

/**
 * @author irwin Timestamp : 22/08/2014 17:51
 */
public class QifDevApplication extends QifEngineApplication {

    public static void main(String[] args) throws Exception {
        QifDevApplication qifDevApplication = new QifDevApplication();
        qifDevApplication.run(args);
    }

    @Override
    protected void initializeApplication(Bootstrap<QifConfig> qifConfigBootstrap) {

    }

    @Override
    protected void runApplication(QifConfig qifConfig, Environment environment) {

    }

    @Override
    protected List<AbstractModule> applicationModule() {
        return null;
    }
}
