package id.co.quadras.qif.dev;

import com.irwin13.winwork.basic.utilities.WinWorkUtil;
import id.co.quadras.qif.dev.guice.GuiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author irwin Timestamp : 07/05/2014 17:59
 */
public class QifDevContextListener implements ServletContextListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(QifDevContextListener.class);
    public static final long START = System.currentTimeMillis();

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        LOGGER.info("================================================================================================");
        LOGGER.info("=================================== Starting QifDevContextListener ===============================");

        String nodeName = System.getProperty("nodeName");
        LOGGER.info("nodeName = {}", nodeName);
        MDC.put("nodeName", WinWorkUtil.getNodeName());

        GuiceFactory.getInjector();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
