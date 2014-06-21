package id.co.quadras.qif.ui.controller;

import com.google.inject.Inject;
import com.irwin13.winwork.basic.model.entity.app.AppPermission;
import id.co.quadras.qif.ui.WebPage;
import id.co.quadras.qif.ui.WebSession;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

/**
 * @author irwin Timestamp : 05/07/13 14:09 *
 */
@Path("/publicAccess/leftMenu")
public class LeftMenuController {

    @Context
    HttpServletRequest request;

    @Inject
    private WebPage webPage;

    @Inject
    private WebSession webSession;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response leftMenu() {
        List<AppPermission> menuList = (List<AppPermission>) webSession.get(request, WebSession.MENU_LIST);

        Map<String, Object> objectMap = webPage.mapWithLoginUser(request);
        objectMap.put(WebSession.MENU_LIST, menuList);
        objectMap.put("isRoot", Boolean.TRUE);

        String content = webPage.stringFromVm(WebPage.COMMON_PAGE_PACKAGE + "leftMenu_macro.vm", objectMap);
        return Response.ok(content).build();
    }

}
