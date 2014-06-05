package id.co.quadras.qif.dev.guice.module;

import com.google.inject.AbstractModule;
import id.co.quadras.qif.core.QifTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Modifier;
import java.util.Set;

/**
 * @author irwin Timestamp : 12/05/2014 17:15
 */
public class TaskModule extends AbstractModule {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskModule.class);

    private final Set<Class<? extends QifTask>> classSet;

    public TaskModule(Set<Class<? extends QifTask>> classSet) {
        this.classSet = classSet;
    }

    @Override
    protected void configure() {
        for (Class<? extends QifTask> taskClass : classSet) {
            if (!Modifier.isAbstract(taskClass.getModifiers())) {
                bind(taskClass);
                LOGGER.info("register QifTask = {}", taskClass.getName());
            }
        }
    }
}
