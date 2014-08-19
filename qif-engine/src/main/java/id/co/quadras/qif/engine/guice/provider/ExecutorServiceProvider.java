package id.co.quadras.qif.engine.guice.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import id.co.quadras.qif.engine.jaxb.Qif;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author irwin Timestamp : 19/08/2014 19:45
 */
public class ExecutorServiceProvider implements Provider<ExecutorService> {

    private final Qif qif;
    private int maxThread = 1024; // default

    @Inject
    public ExecutorServiceProvider(Qif qif) {
        this.qif = qif;
    }

    @Override
    public ExecutorService get() {
        if (qif.getGeneralConfiguration() != null) {
            int max = qif.getGeneralConfiguration().getMaxThreadInThreadPool();
            if (max > 0) {
                maxThread = max;
            }
        }
        return Executors.newFixedThreadPool(maxThread);
    }
}
