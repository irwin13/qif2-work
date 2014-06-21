package id.co.quadras.qif.ui.filter;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.irwin13.winwork.basic.config.WinWorkConfig;
import com.irwin13.winwork.basic.utilities.StringUtil;
import id.co.quadras.qif.ui.WebSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author irwin Timestamp : 12/04/13 18:24
 */
@Singleton
public class UserSessionFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserSessionFilter.class);

    private List<String> noSessionPathList;

    @Inject
    private WebSession webSession;

    @Inject
    private WinWorkConfig config;

    @Override
    public void init(FilterConfig config) throws ServletException {
        String noSessionPath = config.getInitParameter("noSessionPath");
        String[] noSessionPathArray = noSessionPath.split(";");
        noSessionPathList = Arrays.asList(noSessionPathArray);
        LOGGER.debug("noSessionPathList = {}", noSessionPathList);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        LOGGER.debug("<<<<<<<<<<<<< USER SESSION CHECK >>>>>>>>>>>>>>>>>>>");
        LOGGER.debug("request servlet path = {}", request.getServletPath());

        String cookieId = webSession.getCookieId(request);
        if (Strings.isNullOrEmpty(cookieId)) {
            cookieId = StringUtil.random32UUID();
            LOGGER.debug("missing cookie, creating new one with value = {}", cookieId);
        }

        Cookie cookie = new Cookie(WebSession.COOKIE_NAME, cookieId);
        cookie.setPath("/");
        cookie.setMaxAge(config.getInt("webSession.sessionExpire", WebSession.SESSION_EXPIRE));
        response.addCookie(cookie);

        if (!bypassPath(request.getServletPath())) {
            if (webSession.isMainWebSessionActive(request)) {
                LOGGER.debug("Session Active");
            } else {
                LOGGER.debug("Session NOT Active, will be redirect to logout page");
                response.sendRedirect(request.getContextPath() + "/logout");
                return;
            }
        }

        LOGGER.debug("<<<<<<<<<<<<< USER SESSION CHECK FINISH >>>>>>>>>>>>>>>>>>>");
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {}

    private boolean bypassPath(String path) {
        boolean bypass = false;
        for (String bypassPath : noSessionPathList) {
            if (path.contains(bypassPath)) {
                return true;
            }
        }
        return bypass;
    }

}
