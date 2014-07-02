package id.co.quadras.qif.ui.controller.app;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.irwin13.winwork.basic.model.entity.app.AppSetting;
import com.irwin13.winwork.basic.validator.AbstractValidator;
import id.co.quadras.qif.ui.controller.CrudController;
import id.co.quadras.qif.ui.service.app.AppSettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: NovaSinaga
 * Date: 5/2/13
 * Time: 10:51 AM
 *
 */

@Path("/" + AppSetting.MODEL_NAME)
public class AppSettingController extends CrudController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppSettingController.class);
    private static final String PACKAGE_PAGE_PREFIX = "vita/app/";

    @Context
    HttpServletRequest request;

    @Inject
    private AppSettingService appSettingService;

    @Inject
    private @Named(AppSetting.MODEL_NAME)
    AbstractValidator abstractValidator;

    @Override
    protected void setReferenceData(Map<String, Object> objectMap) {
    }

    @Override
    protected void readAdditionalParameter(MultivaluedMap<String, String> formMap, Object model) {
    }

    @GET
    @Path("/list")
    @Produces(MediaType.TEXT_HTML)
    public Response listPage() {
        return basicListPage(request, AppSetting.class, AppSetting.MODEL_NAME);
    }

    @GET
    @Path("/listAjax")
    @Produces(MediaType.TEXT_HTML)
    public Response ajaxListPage() {
        return basicListAjaxPage(request, appSettingService, AppSetting.MODEL_NAME, PACKAGE_PAGE_PREFIX);
    }

    @GET
    @Path("/detailAjax")
    @Produces(MediaType.TEXT_HTML)
    public Response detailPage(@QueryParam("id") String id) {
        LOGGER.debug("id = {}", id);
        return basicDetailPage(request, appSettingService, AppSetting.MODEL_NAME, PACKAGE_PAGE_PREFIX, id);
    }

    @GET
    @Path("/create")
    @Produces(MediaType.TEXT_HTML)
    public Response createPage() {
        return basicCreatePage(request, AppSetting.MODEL_NAME, PACKAGE_PAGE_PREFIX);
    }

    @GET
    @Path("/edit")
    @Produces(MediaType.TEXT_HTML)
    public Response editPage(@QueryParam("id") String id) {
        LOGGER.debug("id = {}", id);
        return basicEditPage(request, appSettingService, AppSetting.MODEL_NAME, PACKAGE_PAGE_PREFIX, id);
    }

    @GET
    @Path("/delete")
    @Produces(MediaType.TEXT_HTML)
    public Response delete(@QueryParam("id") String id) throws URISyntaxException {
        LOGGER.debug("id = {}", id);
        return basicDelete(request, appSettingService, AppSetting.MODEL_NAME, id);
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public Response create(MultivaluedMap<String, String> formMap)
            throws InstantiationException, IllegalAccessException, URISyntaxException {
        return basicCreate(request, appSettingService, AppSetting.class,
                AppSetting.MODEL_NAME, PACKAGE_PAGE_PREFIX, formMap, abstractValidator);
    }

    @POST
    @Path("/edit")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public Response edit(MultivaluedMap<String, String> formMap) throws URISyntaxException {
        return basicEdit(request, appSettingService,
                AppSetting.MODEL_NAME, PACKAGE_PAGE_PREFIX, formMap, abstractValidator);
    }
}
