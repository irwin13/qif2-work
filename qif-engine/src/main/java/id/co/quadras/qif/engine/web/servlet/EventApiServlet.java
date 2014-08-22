package id.co.quadras.qif.engine.web.servlet;

import com.irwin13.winwork.basic.scheduler.BasicSchedulerManager;
import id.co.quadras.qif.engine.QifEngineApplication;
import id.co.quadras.qif.engine.SchedulerStarter;
import id.co.quadras.qif.engine.core.QifActivity;
import id.co.quadras.qif.engine.json.QifJsonParser;
import id.co.quadras.qif.engine.service.EventService;
import id.co.quadras.qif.model.entity.QifEvent;
import id.co.quadras.qif.model.vo.event.EventType;
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
public class EventApiServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventApiServlet.class);
    private final BasicSchedulerManager schedulerManager = QifEngineApplication.getInjector().getInstance(BasicSchedulerManager.class);
    private final SchedulerStarter schedulerStarter = QifEngineApplication.getInjector().getInstance(SchedulerStarter.class);
    private final QifJsonParser qifJsonParser = QifEngineApplication.getInjector().getInstance(QifJsonParser.class);
    private final EventService eventService = QifEngineApplication.getInjector().getInstance(EventService.class);

    // ADD
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = IOUtils.toString(req.getReader());
        LOGGER.debug("json input = {}", json);
        QifEvent qifEvent = qifJsonParser.parseToObject(false, json, QifEvent.class);
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

    // UPDATE
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = IOUtils.toString(req.getReader());
        LOGGER.debug("json input = {}", json);
        QifEvent qifEvent = qifJsonParser.parseToObject(false, json, QifEvent.class);
        LOGGER.debug("update scheduler event = {}", qifEvent);
        if (EventType.SCHEDULER_CRON.getName().equals(qifEvent.getEventType()) ||
                EventType.SCHEDULER_INTERVAL.getName().equals(qifEvent.getEventType())) {
            try {
                schedulerManager.remove(schedulerManager.createTriggerKey(qifEvent.getId()));
                schedulerStarter.startEvent(Arrays.asList(qifEvent));
                eventService.update(qifEvent);
                buildResponse(resp, HttpServletResponse.SC_OK, QifActivity.SUCCESS);
            } catch (SchedulerException e) {
                LOGGER.error(e.getLocalizedMessage(), e);
                buildResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        ExceptionUtils.getStackTrace(e.getCause()));
            }
        } else {
            eventService.update(qifEvent);
            buildResponse(resp, HttpServletResponse.SC_OK, QifActivity.SUCCESS);
        }
    }

    // DELETE
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        LOGGER.debug("delete scheduler event id = {}", id);
        QifEvent qifEvent = eventService.selectById(id);
        if (EventType.SCHEDULER_CRON.getName().equals(qifEvent.getEventType()) ||
                EventType.SCHEDULER_INTERVAL.getName().equals(qifEvent.getEventType())) {

            try {
                schedulerManager.remove(schedulerManager.createTriggerKey(id));
                eventService.delete(qifEvent);
                buildResponse(resp, HttpServletResponse.SC_OK, QifActivity.SUCCESS);
            } catch (SchedulerException e) {
                LOGGER.error(e.getLocalizedMessage(), e);
                buildResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        ExceptionUtils.getStackTrace(e.getCause()));
            }
        } else {
            eventService.delete(qifEvent);
            buildResponse(resp, HttpServletResponse.SC_OK, QifActivity.SUCCESS);
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
