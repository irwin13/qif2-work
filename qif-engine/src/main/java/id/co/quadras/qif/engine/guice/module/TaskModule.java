package id.co.quadras.qif.engine.guice.module;

import com.google.inject.AbstractModule;
import id.co.quadras.qif.engine.core.QifTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Modifier;
import java.util.Set;

/**
 * @author irwin Timestamp : 12/05/2014 17:15
 */
public class TaskModule extends AbstractModule {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskModule.class);

    private final Set<Class<? extends QifTask>> taskSet;

    public TaskModule(Set<Class<? extends QifTask>> taskSet) {
        this.taskSet = taskSet;
    }

    @Override
    protected void configure() {
        for (Class<? extends QifTask> taskClass : taskSet) {
            if (!Modifier.isAbstract(taskClass.getModifiers())) {
                bind(taskClass);
                LOGGER.info("register QifTask = {}", taskClass.getName());
            }
        }
    }
}
