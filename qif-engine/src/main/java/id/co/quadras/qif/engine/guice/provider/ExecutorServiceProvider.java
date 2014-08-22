package id.co.quadras.qif.engine.guice.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import id.co.quadras.qif.engine.config.QifConfig;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author irwin Timestamp : 19/08/2014 19:45
 */
@Singleton
public class ExecutorServiceProvider implements Provider<ExecutorService> {

    private final QifConfig qifConfig;
    private int maxThread = 1024; // default

    @Inject
    public ExecutorServiceProvider(QifConfig qifConfig) {
        this.qifConfig = qifConfig;
    }

    @Override
    public ExecutorService get() {
        int max = qifConfig.getQifTaskMaxConcurrent();
        if (max > 0) {
            maxThread = max;
        }
        return Executors.newFixedThreadPool(maxThread);
    }
}
