package id.co.quadras.qif.engine.web;

import id.co.quadras.qif.engine.core.QifActivity;
import id.co.quadras.qif.engine.guice.QifGuice;
import id.co.quadras.qif.engine.json.QifJsonParser;
import id.co.quadras.qif.engine.service.AdapterService;
import id.co.quadras.qif.model.entity.QifAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Date;

/**
 * @author irwin Timestamp : 02/09/2014 15:56
 */
@Path("/adapter-api")
public class AdapterResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdapterResource.class);
    private final QifJsonParser qifJsonParser = QifGuice.getInjector().getInstance(QifJsonParser.class);
    private final AdapterService adapterService = QifGuice.getInjector().getInstance(AdapterService.class);

    @Context private HttpServletRequest req;

    // UPDATE
    @POST
    public Response post(String json) throws IOException {
        LOGGER.debug("json input = {}", json);
        QifAdapter qifAdapter = qifJsonParser.parseToObject(false, json, QifAdapter.class);
        LOGGER.debug("update qifAdapter = {}", qifAdapter);
        qifAdapter.setLastUpdateDate(new Date());
        adapterService.update(qifAdapter);
        return buildResponse(HttpServletResponse.SC_OK, QifActivity.SUCCESS);
    }

    @DELETE
    public Response doDelete(@QueryParam("id") String id) throws IOException {
        LOGGER.debug("delete scheduler event id = {}", id);
        QifAdapter qifAdapter = adapterService.selectById(id);
        qifAdapter.setLastUpdateDate(new Date());
        qifAdapter.setActive(Boolean.FALSE);
        adapterService.update(qifAdapter);
        return buildResponse(HttpServletResponse.SC_OK, QifActivity.SUCCESS);
    }

    private Response buildResponse(int statusCode, String message) {
        return Response.status(statusCode).entity(message).build();
    }

}
