package id.co.quadras.qif.ui.dao;

import com.google.inject.Inject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * @author irwin Timestamp : 02/07/13 16:27
 */
public class SessionFactoryEntityResolver implements EntityResolver {

    private final SessionFactory sessionFactory;

    @Inject
    public SessionFactoryEntityResolver(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public <T> T getById(String id, Class<T> clazz) {
        Session session = sessionFactory.openSession();
        T t = null;
        try {
            t = (T) session.get(clazz, id);
        } finally {
            if (session != null) session.close();
        }
        return t;
    }
}
