package id.co.quadras.qif.ui.controller;

import com.google.inject.Inject;
import com.irwin13.winwork.basic.config.WinWorkConfig;
import com.irwin13.winwork.basic.model.entity.app.AppUser;
import com.irwin13.winwork.basic.utilities.SecurityUtil;
import id.co.quadras.qif.ui.WebPage;
import id.co.quadras.qif.ui.WebSession;
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
import java.util.Map;

@Path("/changePassword")
public class ChangePasswordController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChangePasswordController.class);
    private static final String PACKAGE_COMMON_PAGE = "vita/common/";

    @Context
    private HttpServletRequest request;

    @Inject
    private WebPage webPage;

    @Inject
    private AppUserService appUserService;

    @Inject
    private WinWorkConfig configuration;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public Response changePassword(MultivaluedMap<String,String> formMap) throws URISyntaxException,
            NoSuchAlgorithmException {
        Map<String, Object> objectMap = webPage.mapWithLoginUser(request);
        AppUser appUser = (AppUser) objectMap.get(WebSession.LOGIN_USER);

        String oldPassword = formMap.getFirst("currentPassword");
        String newPassword = formMap.getFirst("newPassword");
        String hashOldPassword = null;

        try {
            hashOldPassword = SecurityUtil.createHash(oldPassword, SecurityUtil.DEFAULT_HASH);
        }
        catch (NoSuchAlgorithmException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }

        if (hashOldPassword.equalsIgnoreCase(appUser.getPassword())) {
            AppUser user = appUserService.getById(appUser.getId(), true);
            user.setPassword(SecurityUtil.createHash(newPassword, SecurityUtil.DEFAULT_HASH));
            appUserService.update(user);
            String webContext = configuration.getString("web.context");
            LOGGER.debug("webContext to redirect = {}", webContext);
            return webPage.redirectResponse(webContext + "/logout");
        } else {
            objectMap.put("errorMessage", "Current Password Salah");
            String content = webPage.stringFromVm(PACKAGE_COMMON_PAGE + "changePassword.vm",objectMap);
            return webPage.okResponse(content);
        }
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response changePasswordBasic() {
        Map<String, Object> objectMap = webPage.mapWithLoginUser(request);
        objectMap.put("errorMessage", "");
        String content = webPage.stringFromVm(PACKAGE_COMMON_PAGE + "changePassword.vm",objectMap);
        return webPage.okResponse(content);
    }
}
