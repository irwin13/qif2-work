package id.co.quadras.qif.engine.web.servlet;

import id.co.quadras.qif.engine.core.QifActivity;
import id.co.quadras.qif.engine.guice.QifGuice;
import id.co.quadras.qif.engine.json.QifJsonParser;
import id.co.quadras.qif.engine.service.AdapterService;
import id.co.quadras.qif.model.entity.QifAdapter;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @author irwin Timestamp : 28/08/2014 19:47
 */
public class AdapterApiServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventApiServlet.class);
    private final QifJsonParser qifJsonParser = QifGuice.getInjector().getInstance(QifJsonParser.class);
    private final AdapterService adapterService = QifGuice.getInjector().getInstance(AdapterService.class);

    // UPDATE
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = IOUtils.toString(req.getReader());
        LOGGER.debug("json input = {}", json);
        QifAdapter qifAdapter = qifJsonParser.parseToObject(false, json, QifAdapter.class);
        LOGGER.debug("update qifAdapter = {}", qifAdapter);
        qifAdapter.setLastUpdateDate(new Date());
        adapterService.update(qifAdapter);
        buildResponse(resp, HttpServletResponse.SC_OK, QifActivity.SUCCESS);
    }

    // DELETE
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        LOGGER.debug("delete scheduler event id = {}", id);
        QifAdapter qifAdapter = adapterService.selectById(id);
        qifAdapter.setLastUpdateDate(new Date());
        qifAdapter.setActive(Boolean.FALSE);
        adapterService.update(qifAdapter);
        buildResponse(resp, HttpServletResponse.SC_OK, QifActivity.SUCCESS);
    }

    private void buildResponse(HttpServletResponse response, int statusCode, String message)
            throws IOException {

        response.setContentType(EventDispatcherServlet.TEXT_PLAIN);
        response.setStatus(statusCode);
        if (message != null) {
            response.setContentLength(message.length());
        }
        response.getWriter().println(message);
    }

}
