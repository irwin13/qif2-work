package id.co.quadras.qif.engine.process;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import id.co.quadras.qif.engine.core.QifProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

/**
 * @author irwin Timestamp : 05/06/2014 20:01
 */
public class ProcessRegister {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessRegister.class);

    private static Set<Class<? extends QifProcess>> processSet;

    public static void init(String rootPackage) {
        if (processSet == null) {
            ClassPath classPath;
            try {
                classPath = ClassPath.from(Thread.currentThread().getContextClassLoader());
            } catch (IOException e) {
                LOGGER.error(e.getLocalizedMessage(), e);
                throw new RuntimeException(e.getMessage());
            }

            ImmutableSet<ClassPath.ClassInfo> immutableSetProcess = classPath.getTopLevelClassesRecursive(rootPackage);

            processSet = new HashSet<Class<? extends QifProcess>>();
            for (ClassPath.ClassInfo classInfo : immutableSetProcess) {
                if (!Modifier.isAbstract(classInfo.load().getModifiers())) {
                    processSet.add((Class<? extends QifProcess>) classInfo.load());
                }
            }
        }
    }

    public static Set<Class<? extends QifProcess>> getProcessSet() {
        return processSet;
    }
}
