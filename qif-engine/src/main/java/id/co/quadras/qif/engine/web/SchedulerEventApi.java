package id.co.quadras.qif.engine.web;

import com.irwin13.winwork.basic.scheduler.BasicSchedulerManager;
import id.co.quadras.qif.core.QifActivity;
import id.co.quadras.qif.core.helper.JsonParser;
import id.co.quadras.qif.core.model.entity.QifEvent;
import id.co.quadras.qif.engine.SchedulerStarter;
import id.co.quadras.qif.engine.guice.EngineFactory;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author irwin Timestamp : 13/06/2014 18:49
 */
public class SchedulerEventApi extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerEventApi.class);
    private final BasicSchedulerManager schedulerManager = EngineFactory.getInjector().getInstance(BasicSchedulerManager.class);
    private final SchedulerStarter schedulerStarter = EngineFactory.getInjector().getInstance(SchedulerStarter.class);
    private final JsonParser jsonParser = EngineFactory.getInjector().getInstance(JsonParser.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = IOUtils.toString(req.getReader());
        LOGGER.debug("json input = {}", json);
        QifEvent qifEvent = jsonParser.parseToObject(false, json, QifEvent.class);
        LOGGER.debug("update scheduler event = {}", qifEvent);
        try {
            schedulerManager.remove(schedulerManager.createTriggerKey(qifEvent.getId()));
            schedulerStarter.startEvent(Arrays.asList(qifEvent));
            buildResponse(resp, HttpServletResponse.SC_OK, QifActivity.SUCCESS);
        } catch (SchedulerException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
            buildResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    ExceptionUtils.getStackTrace(e.getCause()));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = IOUtils.toString(req.getReader());
        LOGGER.debug("json input = {}", json);
        QifEvent qifEvent = jsonParser.parseToObject(false, json, QifEvent.class);
        LOGGER.debug("add scheduler event = {}", qifEvent);
        try {
            schedulerStarter.startEvent(Arrays.asList(qifEvent));
            buildResponse(resp, HttpServletResponse.SC_OK, QifActivity.SUCCESS);
        } catch (SchedulerException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
            buildResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    ExceptionUtils.getStackTrace(e.getCause()));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        LOGGER.debug("delete scheduler event id = {}", id);
        try {
            schedulerManager.remove(schedulerManager.createTriggerKey(id));
            buildResponse(resp, HttpServletResponse.SC_OK, QifActivity.SUCCESS);
        } catch (SchedulerException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
            buildResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    ExceptionUtils.getStackTrace(e.getCause()));
        }
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
