package id.co.quadras.qif.ui.controller;

import com.google.inject.Inject;
import id.co.quadras.qif.ui.WebPage;
import id.co.quadras.qif.ui.WebSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * @author irwin Timestamp : 05/07/13 14:09 *
 */
@Path("/logout")
public class LogoutController {

    @Context
    HttpServletRequest request;
    @Context
    HttpServletResponse response;

    @Inject
    private WebSession webSession;

    @Inject
    private WebPage webPage;

    @GET
    public Response logout() {
        webSession.invalidate(request, response);
        String content = webPage.stringFromVm(WebPage.COMMON_PAGE_PACKAGE + "logout.vm", null);
        return Response.ok(content).build();
    }

}
