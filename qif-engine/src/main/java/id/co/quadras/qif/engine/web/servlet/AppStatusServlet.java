package id.co.quadras.qif.engine.web.servlet;

import com.irwin13.winwork.basic.WinWorkConstants;
import com.irwin13.winwork.basic.config.WinWorkConfig;
import com.irwin13.winwork.basic.utilities.WinWorkUtil;
import id.co.quadras.qif.engine.QifEngine;
import id.co.quadras.qif.engine.guice.QifGuiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author irwin Timestamp : 18/05/2014 1:12
 */
public class AppStatusServlet extends HttpServlet {

    private static final String TEXT_HTML = "text/html";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        WinWorkConfig winWorkConfig = QifGuiceFactory.getInjector().getInstance(WinWorkConfig.class);
        String htmlContent = WinWorkUtil.appStatusView(winWorkConfig, QifEngine.START,
                WinWorkConstants.SDF_FULL);

        resp.setContentType(TEXT_HTML);
        resp.setContentLength(htmlContent.length());
        resp.setCharacterEncoding(WinWorkConstants.UTF_8);
        resp.setStatus(HttpServletResponse.SC_OK);

        resp.getWriter().append(htmlContent);
    }
}
