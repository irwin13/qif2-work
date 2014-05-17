package id.co.quadras.qif.dev.guice.module;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.irwin13.winwork.basic.config.WinWorkConfig;
import id.co.quadras.qif.dev.QifConfig;

/**
 * @author irwin Timestamp : 12/05/2014 17:15
 */
public class SharedModule extends AbstractModule {

    @Override
    protected void configure() {

        // configuration file
        bind(String.class)
                .annotatedWith(Names.named("configFile"))
                .toInstance("common-config.xml");

        bind(String.class)
                .annotatedWith(Names.named("myBatisConfigFile"))
                .toInstance("mybatis-config.xml");

        bind(WinWorkConfig.class).to(QifConfig.class).in(Singleton.class);

    }
}
