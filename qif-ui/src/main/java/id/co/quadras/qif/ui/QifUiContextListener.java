package id.co.quadras.qif.ui;

import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.irwin13.winwork.basic.utilities.WinWorkUtil;
import com.irwin13.winwork.basic.utilities.WinWorkVelocityUtil;
import id.co.quadras.qif.ui.guice.GuiceQifUi;
import org.apache.velocity.app.Velocity;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.servlet.ServletContextEvent;
import java.util.Properties;

/**
 * @author irwin Timestamp : 20/06/2014 16:57
 */
public class QifUiContextListener extends GuiceServletContextListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(QifUiContextListener.class);
    public static final long START = System.currentTimeMillis();

    @Override
    protected Injector getInjector() {
        return GuiceQifUi.getInjector();
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        MDC.put("nodeName", WinWorkUtil.getNodeName());
        LOGGER.info("================================================================================================");
        LOGGER.info("=================================== Starting QifWebContextListener ===============================");

        String activateCache = servletContextEvent.getServletContext().getInitParameter("activateCache");
        String modificationCheckInterval = servletContextEvent.getServletContext().getInitParameter("modificationCheckInterval");
        String rootUrl = servletContextEvent.getServletContext().getInitParameter("rootUrl");

        LOGGER.info("init param activateCache = {}", activateCache);
        LOGGER.info("init param modificationCheckInterval = {}", modificationCheckInterval);
        LOGGER.info("init param rootUrl = {}", rootUrl);

        super.contextInitialized(servletContextEvent);
        initVelocity(activateCache, modificationCheckInterval, rootUrl);
    }

    private void initVelocity(String activateCache, String modificationCheckInterval, String rootUrl) {
        Properties velocityProperties = WinWorkVelocityUtil.urlProperties(activateCache, modificationCheckInterval, rootUrl);
        Velocity.init(velocityProperties);
        LOGGER.info("init VELOCITY with param = {}", velocityProperties);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        MDC.put("nodeName", WinWorkUtil.getNodeName());
        LOGGER.info("================================================================================================");
        LOGGER.info("=================================== Shutdown QifWebContextListener ===============================");

        LOGGER.info("Shutdown Hibernate ...");
        SessionFactory sessionFactory = GuiceQifUi.getInjector().getInstance(SessionFactory.class);
        sessionFactory.close();
        LOGGER.info("Shutdown Hibernate complete");

        super.contextDestroyed(servletContextEvent);
    }

}
