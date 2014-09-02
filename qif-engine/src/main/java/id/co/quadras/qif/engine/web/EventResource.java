package id.co.quadras.qif.engine.web;

import com.irwin13.winwork.basic.scheduler.BasicSchedulerManager;
import id.co.quadras.qif.engine.SchedulerStarter;
import id.co.quadras.qif.engine.core.QifActivity;
import id.co.quadras.qif.engine.guice.QifGuice;
import id.co.quadras.qif.engine.json.QifJsonParser;
import id.co.quadras.qif.engine.service.EventService;
import id.co.quadras.qif.model.entity.QifEvent;
import id.co.quadras.qif.model.vo.event.EventType;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author irwin Timestamp : 02/09/2014 15:56
 */
@Path("/event-api")
public class EventResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventResource.class);
    private final BasicSchedulerManager schedulerManager = QifGuice.getInjector().getInstance(BasicSchedulerManager.class);
    private final SchedulerStarter schedulerStarter = QifGuice.getInjector().getInstance(SchedulerStarter.class);
    private final QifJsonParser qifJsonParser = QifGuice.getInjector().getInstance(QifJsonParser.class);
    private final EventService eventService = QifGuice.getInjector().getInstance(EventService.class);

    @Context
    private HttpServletRequest req;

    // ADD
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    public Response doPut() throws IOException {
        String json = IOUtils.toString(req.getReader());
        LOGGER.debug("json input = {}", json);
        QifEvent qifEvent = qifJsonParser.parseToObject(false, json, QifEvent.class);
        LOGGER.debug("add scheduler event = {}", qifEvent);
        try {
            schedulerStarter.startEvent(Arrays.asList(qifEvent));
            return buildResponse(HttpServletResponse.SC_OK, QifActivity.SUCCESS);
        } catch (SchedulerException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
            return buildResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    ExceptionUtils.getStackTrace(e.getCause()));
        }
    }

    // UPDATE
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response doPost() throws IOException {
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
                return buildResponse(HttpServletResponse.SC_OK, QifActivity.SUCCESS);
            } catch (SchedulerException e) {
                LOGGER.error(e.getLocalizedMessage(), e);
                return buildResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        ExceptionUtils.getStackTrace(e.getCause()));
            }
        } else {
            eventService.update(qifEvent);
            return buildResponse(HttpServletResponse.SC_OK, QifActivity.SUCCESS);
        }
    }

    // DELETE
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public Response doDelete() throws IOException {
        String id = req.getParameter("id");
        LOGGER.debug("delete scheduler event id = {}", id);
        QifEvent qifEvent = eventService.selectById(id);
        if (EventType.SCHEDULER_CRON.getName().equals(qifEvent.getEventType()) ||
                EventType.SCHEDULER_INTERVAL.getName().equals(qifEvent.getEventType())) {

            try {
                schedulerManager.remove(schedulerManager.createTriggerKey(id));
                eventService.delete(qifEvent);
                return buildResponse(HttpServletResponse.SC_OK, QifActivity.SUCCESS);
            } catch (SchedulerException e) {
                LOGGER.error(e.getLocalizedMessage(), e);
                return buildResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        ExceptionUtils.getStackTrace(e.getCause()));
            }
        } else {
            eventService.delete(qifEvent);
            return buildResponse(HttpServletResponse.SC_OK, QifActivity.SUCCESS);
        }
    }

    private Response buildResponse(int statusCode, String message) {
        return Response.status(statusCode).entity(message).build();
    }


}
