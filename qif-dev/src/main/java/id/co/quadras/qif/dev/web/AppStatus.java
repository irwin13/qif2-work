package id.co.quadras.qif.dev.web;

import com.irwin13.winwork.basic.WinWorkConstants;
import com.irwin13.winwork.basic.config.WinWorkConfig;
import com.irwin13.winwork.basic.utilities.WinWorkUtil;
import id.co.quadras.qif.dev.QifDevContextListener;
import id.co.quadras.qif.dev.guice.GuiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author irwin Timestamp : 18/05/2014 1:12
 */
public class AppStatus extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        WinWorkConfig winWorkConfig = GuiceFactory.getInjector().getInstance(WinWorkConfig.class);
        String htmlContent = WinWorkUtil.appStatusView(winWorkConfig, QifDevContextListener.START,
                WinWorkConstants.SDF_FULL);

        resp.getWriter().append(htmlContent);
    }
}
