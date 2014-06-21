package id.co.quadras.qif.ui.controller.app;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.irwin13.winwork.basic.model.entity.app.AppOption;
import com.irwin13.winwork.basic.model.entity.app.AppPermission;
import com.irwin13.winwork.basic.validator.AbstractValidator;
import id.co.quadras.qif.ui.controller.CrudController;
import id.co.quadras.qif.ui.service.app.AppOptionService;
import id.co.quadras.qif.ui.service.app.AppPermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

/**
 * @author irwin Timestamp : 23/04/13 14:08
 */
@Path("/" + AppPermission.MODEL_NAME)
public class AppPermissionController extends CrudController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppPermissionController.class);
    private static final String PACKAGE_PAGE_PREFIX = "vita/app/";

    @Context
    HttpServletRequest request;

    @Inject
    private AppPermissionService appPermissionService;

    @Inject
    private @Named(AppPermission.MODEL_NAME)
    AbstractValidator validator;

    @Inject
    private AppOptionService appOptionService;

    @Override
    protected void setReferenceData(Map<String, Object> objectMap) {
        List<AppOption> httpMethodList = appOptionService.selectByCategory("http_method");
        objectMap.put("httpMethodList", httpMethodList);

        List<AppPermission> appPermissionList = appPermissionService.sortMenuByLevel(appPermissionService.getNullParent());
        objectMap.put("appPermissionList", appPermissionList);
    }

    @Override
    protected void readAdditionalParameter(MultivaluedMap<String, String> formMap, Object model) {}

    @GET
    @Path("/list")
    @Produces(MediaType.TEXT_HTML)
    public Response listPage() {
        return basicListPage(request, AppPermission.class, AppPermission.MODEL_NAME);
    }

    @GET
    @Path("/listAjax")
    @Produces(MediaType.TEXT_HTML)
    public Response ajaxListPage() {
        return basicListAjaxPage(request, appPermissionService, AppPermission.MODEL_NAME, PACKAGE_PAGE_PREFIX);
    }

    @GET
    @Path("/detail")
    @Produces(MediaType.TEXT_HTML)
    public Response detailPage(@QueryParam("id") String id) {
        LOGGER.debug("id = {}", id);
        return basicDetailPage(request, appPermissionService, AppPermission.MODEL_NAME, PACKAGE_PAGE_PREFIX, id);
    }

    @GET
    @Path("/create")
    @Produces(MediaType.TEXT_HTML)
    public Response createPage() {
        return basicCreatePage(request, AppPermission.MODEL_NAME, PACKAGE_PAGE_PREFIX);
    }

    @GET
    @Path("/edit")
    @Produces(MediaType.TEXT_HTML)
    public Response editPage(@QueryParam("id") String id) {
        LOGGER.debug("id = {}", id);
        return basicEditPage(request, appPermissionService, AppPermission.MODEL_NAME, PACKAGE_PAGE_PREFIX, id);
    }

    @GET
    @Path("/delete")
    @Produces(MediaType.TEXT_HTML)
    public Response delete(@QueryParam("id") String id) throws URISyntaxException {
        LOGGER.debug("id = {}", id);
        return basicDelete(request, appPermissionService, AppPermission.MODEL_NAME, id);
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public Response create(MultivaluedMap<String, String> formMap)
            throws InstantiationException, IllegalAccessException, URISyntaxException {

        return basicCreate(request, appPermissionService, AppPermission.class,
                AppPermission.MODEL_NAME, PACKAGE_PAGE_PREFIX, formMap, validator);
    }

    //    @PUT -- somehow http form @method doesn't support PUT, so we use POST instead
    @POST
    @Path("/edit")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public Response edit(MultivaluedMap<String, String> formMap) throws URISyntaxException {
        return basicEdit(request, appPermissionService, AppPermission.MODEL_NAME, PACKAGE_PAGE_PREFIX, formMap, validator);
    }

}
