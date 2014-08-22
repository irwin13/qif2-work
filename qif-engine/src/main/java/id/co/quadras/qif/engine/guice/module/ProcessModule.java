package id.co.quadras.qif.engine.guice.module;

import com.google.inject.AbstractModule;
import id.co.quadras.qif.engine.core.QifProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Modifier;
import java.util.Set;

/**
 * @author irwin Timestamp : 12/05/2014 17:15
 */
public class ProcessModule extends AbstractModule {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessModule.class);

    private final Set<Class<? extends QifProcess>> processSet;

    public ProcessModule(Set<Class<? extends QifProcess>> processSet) {
        this.processSet = processSet;
    }

    @Override
    protected void configure() {
        for (Class<? extends QifProcess> processClass : processSet) {
            if (!Modifier.isAbstract(processClass.getModifiers())) {
                bind(processClass);
                LOGGER.info("register QifProcess = {} ", processClass.getName());
            }
        }
    }
}
