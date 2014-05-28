package id.co.quadras.qif.helper;

import com.google.inject.Injector;

/**
 * @author irwin Timestamp : 28/05/2014 11:26
 */
public class TaskRepository {

    public static <T> T getTask(final Injector injector, final Class<T> taskClass) {
        return injector.getInstance(taskClass);
    }
}
