package id.co.quadras.qif.ui.controller.app;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.irwin13.winwork.basic.WinWorkConstants;
import com.irwin13.winwork.basic.model.SortParameter;
import com.irwin13.winwork.basic.model.entity.app.AppRole;
import com.irwin13.winwork.basic.model.entity.app.AppUser;
import com.irwin13.winwork.basic.utilities.SecurityUtil;
import com.irwin13.winwork.basic.validator.AbstractValidator;
import id.co.quadras.qif.ui.controller.CrudController;
import id.co.quadras.qif.ui.service.app.AppRoleService;
import id.co.quadras.qif.ui.service.app.AppUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author irwin Timestamp : 23/04/13 14:08
 */
@Path("/" + AppUser.MODEL_NAME)
public class AppUserController extends CrudController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppUserController.class);
    private static final String PACKAGE_PAGE_PREFIX = "vita/app/";

    @Context
    HttpServletRequest request;

    @Inject
    private AppUserService appUserService;

    @Inject
    private @Named(AppUser.MODEL_NAME)
    AbstractValidator validator;

    @Inject
    private AppRoleService appRoleService;

    @GET
    @Path("/list")
    @Produces(MediaType.TEXT_HTML)
    public Response listPage() {
        return basicListPage(request, AppUser.class, AppUser.MODEL_NAME);
    }

    @GET
    @Path("/listAjax")
    @Produces(MediaType.TEXT_HTML)
    public Response ajaxListPage() {
        return basicListAjaxPage(request, appUserService, AppUser.MODEL_NAME, PACKAGE_PAGE_PREFIX);
    }

    @GET
    @Path("/detail")
    @Produces(MediaType.TEXT_HTML)
    public Response detailPage(@QueryParam("id") String id) {
        LOGGER.debug("id = {}", id);
        return basicDetailPage(request, appUserService, AppUser.MODEL_NAME, PACKAGE_PAGE_PREFIX, id);
    }

    @GET
    @Path("/create")
    @Produces(MediaType.TEXT_HTML)
    public Response createPage() {
        return basicCreatePage(request, AppUser.MODEL_NAME, PACKAGE_PAGE_PREFIX);
    }

    @GET
    @Path("/edit")
    @Produces(MediaType.TEXT_HTML)
    public Response editPage(@QueryParam("id") String id) {
        LOGGER.debug("id = {}", id);
        return basicEditPage(request, appUserService, AppUser.MODEL_NAME, PACKAGE_PAGE_PREFIX, id);
    }

    @GET
    @Path("/delete")
    @Produces(MediaType.TEXT_HTML)
    public Response delete(@QueryParam("id") String id) throws URISyntaxException {
        LOGGER.debug("id = {}", id);
        return basicDelete(request, appUserService, AppUser.MODEL_NAME, id);
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public Response create(MultivaluedMap<String, String> formMap)
            throws InstantiationException, IllegalAccessException, URISyntaxException {
        return basicCreate(request, appUserService, AppUser.class,
                AppUser.MODEL_NAME, PACKAGE_PAGE_PREFIX, formMap, validator);
    }

    //    @PUT -- somehow http form @method doesn't support PUT, so we use POST instead
    @POST
    @Path("/edit")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public Response edit(MultivaluedMap<String, String> formMap) throws URISyntaxException {
        return basicEdit(request, appUserService, AppUser.MODEL_NAME, PACKAGE_PAGE_PREFIX, formMap, validator);
    }

    @Override
    protected void setReferenceData(Map<String, Object> objectMap) {
        AppRole filter = new AppRole();
        filter.setActive(Boolean.TRUE);
        List<AppRole> appRoleList = appRoleService.select(filter, new SortParameter("name", SortParameter.ASC));
        List<AppRole> filteredList = new LinkedList<AppRole>();
        for (AppRole appRole : appRoleList) {
            if (!appRole.getName().equalsIgnoreCase("admin")) {
                filteredList.add(appRole);
            }
        }

        objectMap.put("appRoleList", filteredList);
    }

    @GET
    @Path(value="/resetPassword")
    @Produces(MediaType.TEXT_HTML)
    public Response resetPassword(@QueryParam("id") String id) throws URISyntaxException, NoSuchAlgorithmException {

        if (!Strings.isNullOrEmpty(id)) {
            AppUser appUser = appUserService.getById(id, true);
            if (appUser != null) {
                webPage.setUserLogged(request, appUser, true);
                appUser.setPassword(SecurityUtil.createHash(WinWorkConstants.DEFAULT_PASSWORD, SecurityUtil.DEFAULT_HASH));
                appUserService.update(appUser);
                LOGGER.debug("user {} password has been reset ", appUser.getUsername());
            }
        }

        return webPage.redirectListPage(AppUser.MODEL_NAME);
    }

    @Override
    protected void readAdditionalParameter(MultivaluedMap<String, String> formMap, Object model) {
        AppUser appUser = (AppUser) model;
        List<AppRole> list = new LinkedList<AppRole>();
        AppRole filter = new AppRole();
        filter.setActive(Boolean.TRUE);
        List<AppRole> appRoleList = appRoleService.select(filter, null);
        if (appRoleList != null) {
            for (AppRole appRole : appRoleList) {
                if (formMap.getFirst("appRole_" + appRole.getId()) != null) {
                    list.add(appRole);
                }
            }
        }
        appUser.setAppRoleList(list);
    }

}
