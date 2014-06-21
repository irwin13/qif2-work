package id.co.quadras.qif.ui.controller;

import com.google.inject.Inject;
import id.co.quadras.qif.ui.WebPage;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author irwin Timestamp : 05/07/13 14:09 *
 */
@Path("/")
public class MainController {

    @Context
    HttpServletRequest request;

    @Inject
    private WebPage webPage;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response indexPage() {
        String content = webPage.stringFromVm(WebPage.COMMON_PAGE_PACKAGE + "main.vm", webPage.mapWithLoginUser(request));
        return Response.ok(content).build();
    }
}
