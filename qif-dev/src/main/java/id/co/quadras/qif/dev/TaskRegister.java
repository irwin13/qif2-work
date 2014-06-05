package id.co.quadras.qif.dev;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import id.co.quadras.qif.core.QifTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author irwin Timestamp : 05/06/2014 20:01
 */
public class TaskRegister {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskRegister.class);

    private static Set<Class<? extends QifTask>> taskSet;

    public static void init(String rootPackage) {
        if (taskSet == null) {
            ClassPath classPath;
            try {
                classPath = ClassPath.from(Thread.currentThread().getContextClassLoader());
            } catch (IOException e) {
                LOGGER.error(e.getLocalizedMessage(), e);
                throw new RuntimeException(e.getMessage());
            }

            ImmutableSet<ClassPath.ClassInfo> immutableSetTask = classPath.getTopLevelClassesRecursive(rootPackage);

            taskSet = new HashSet<Class<? extends QifTask>>();
            for (ClassPath.ClassInfo classInfo : immutableSetTask) {
                taskSet.add((Class<? extends QifTask>) classInfo.load());
            }
        }
    }

    public static Set<Class<? extends QifTask>> getTaskSet() {
        return taskSet;
    }

}
