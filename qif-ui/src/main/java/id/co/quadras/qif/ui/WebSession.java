package id.co.quadras.qif.ui;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.irwin13.winwork.basic.config.WinWorkConfig;
import com.irwin13.winwork.basic.utilities.RestClient;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author irwin Timestamp : 12/04/13 16:23
 */
public class WebSession {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSession.class);

    public static final String REDIS = "Redis";
    public static final String HTTP_SESSION = "HttpSession";
    private static final String WEB_SESSION = "WinWorkSession";
    private static final String MAIN_SESSION = "MainSession";

    private static final String WEB_SESSION_GET = "webSession/get";
    private static final String WEB_SESSION_SET = "webSession/set";
    private static final String WEB_SESSION_REMOVE = "webSession/remove";
    private static final String WEB_SESSION_EXISTS = "webSession/exists";

    public static final String COOKIE_NAME = "WinWorkCookie";
    public static final String LOGIN_USER = "loginUser";
    public static final String MENU_LIST = "userMenuList";
    public static final String USER_PERMISSION_LIST = "userPermissionList";
    public static final int SESSION_EXPIRE = 1800; // in seconds, so 1800 means 30 minutes

    private final String storageSystem;
    private final WinWorkConfig config;
    private final RestClient restClient;

    @Inject
    public WebSession(@Named("webSessionStorageSystem") String storageSystem, WinWorkConfig config,
                      RestClient restClient) {
        this.storageSystem = storageSystem;
        this.config = config;
        this.restClient = restClient;
    }

    public void createMainWebSession(HttpServletRequest request) {
        String sessionContent = mainSessionContent(request);
        if (storageSystem.equalsIgnoreCase(REDIS)) {
            String key = defaultNamespaceWebSessionKey(getCookieId(request), MAIN_SESSION);

            String redisApiContext = config.getString("redisApi.context");
            String url = redisApiContext + WEB_SESSION_SET;
            LOGGER.trace("url = {}", url);

            int seconds = config.getInt("webSession.sessionExpire", SESSION_EXPIRE);

            Map<String, String> parameter = new HashMap<String, String>();
            parameter.put("key", key);
            parameter.put("content", mainSessionContent(request));
            parameter.put("seconds", String.valueOf(seconds));

            try {
                restClient.post(url, parameter);
            } catch (IOException e) {
                LOGGER.error(e.getLocalizedMessage(), e);
            }
        } else if (storageSystem.equalsIgnoreCase(HTTP_SESSION)) {
            request.getSession().setAttribute(MAIN_SESSION, sessionContent);
        }
    }

    public void updateMainWebSession(HttpServletRequest request) {
        if (isMainWebSessionActive(request)) {
            String mainSessionContent = mainSessionContent(request);

            if (storageSystem.equalsIgnoreCase(REDIS)) {
                String redisApiContext = config.getString("redisApi.context");
                String url = redisApiContext + WEB_SESSION_SET;
                LOGGER.trace("url = {}", url);
                int seconds = config.getInt("webSession.sessionExpire", SESSION_EXPIRE);

                String mainSessionKey = defaultNamespaceWebSessionKey(getCookieId(request), MAIN_SESSION);
                Map<String, String> parameterMainSession = new HashMap<String, String>();
                parameterMainSession.put("key", mainSessionKey);
                parameterMainSession.put("content", mainSessionContent);
                parameterMainSession.put("seconds", String.valueOf(seconds));

                String loginUserKey = defaultNamespaceWebSessionKey(getCookieId(request), LOGIN_USER);
                Map<String, String> parameterLoginUser = new HashMap<String, String>();
                parameterLoginUser.put("key", loginUserKey);
                parameterLoginUser.put("content", (String) get(request, LOGIN_USER));
                parameterLoginUser.put("seconds", String.valueOf(seconds));

                String menuListKey = defaultNamespaceWebSessionKey(getCookieId(request), MENU_LIST);
                Map<String, String> parameterMenuList = new HashMap<String, String>();
                parameterMenuList.put("key", menuListKey);
                parameterMenuList.put("content", (String) get(request, MENU_LIST));
                parameterMenuList.put("seconds", String.valueOf(seconds));

                String permissionListKey = defaultNamespaceWebSessionKey(getCookieId(request), USER_PERMISSION_LIST);
                Map<String, String> parameterPermissionList = new HashMap<String, String>();
                parameterPermissionList.put("key", permissionListKey);
                parameterPermissionList.put("content", (String) get(request, USER_PERMISSION_LIST));
                parameterPermissionList.put("seconds", String.valueOf(seconds));

                try {
                    restClient.post(url, parameterMainSession);
                    restClient.post(url, parameterLoginUser);
                    restClient.post(url, parameterMenuList);
                    restClient.post(url, parameterPermissionList);
                } catch (IOException e) {
                    LOGGER.error(e.getLocalizedMessage(), e);
                }

            } else if (storageSystem.equalsIgnoreCase(HTTP_SESSION)) {
                request.getSession().setAttribute(MAIN_SESSION, mainSessionContent);
            }
        }
    }

    private String mainSessionContent(HttpServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(request.getRemoteAddr()) // client IP address
                .append("-")
                .append(new DateTime()); // timestamp last session activity
        return stringBuilder.toString();
    }

    public boolean isMainWebSessionActive(HttpServletRequest request) {
        boolean result = false;

        if (storageSystem.equalsIgnoreCase(REDIS)) {
            String key = defaultNamespaceWebSessionKey(getCookieId(request), MAIN_SESSION);

            String redisApiContext = config.getString("redisApi.context");
            String url = redisApiContext + WEB_SESSION_EXISTS + "?key=" + key;
            LOGGER.trace("url = {}", url);
            try {
                String exists = restClient.get(url);
                result = Boolean.valueOf(exists);
            } catch (IOException e) {
                LOGGER.error(e.getLocalizedMessage(), e);
            }
        } else if (storageSystem.equalsIgnoreCase(HTTP_SESSION)) {
            result = (request.getSession().getAttribute(MAIN_SESSION) != null);
        }

        return result;
    }

    public void set(HttpServletRequest request, String name, Object value) {
        if (storageSystem.equalsIgnoreCase(REDIS)) {
            String redisApiContext = config.getString("redisApi.context");
            String url = redisApiContext + WEB_SESSION_SET;
            LOGGER.trace("url = {}", url);
            int seconds = config.getInt("webSession.sessionExpire", SESSION_EXPIRE);

            String key = defaultNamespaceWebSessionKey(getCookieId(request), name);
            Map<String, String> parameter = new HashMap<String, String>();
            parameter.put("key", key);
            parameter.put("content", (String) value);
            parameter.put("seconds", String.valueOf(seconds));

            try {
                restClient.post(url, parameter);
            } catch (IOException e) {
                LOGGER.error(e.getLocalizedMessage(), e);
            }
        } else if (storageSystem.equalsIgnoreCase(HTTP_SESSION)) {
            request.getSession().setAttribute(name, value);
        }
    }

    public Object get(HttpServletRequest request, String name) {
        Object result = null;
        if (storageSystem.equalsIgnoreCase(REDIS)) {
            String key = defaultNamespaceWebSessionKey(getCookieId(request), name);
            String redisApiContext = config.getString("redisApi.context");
            String url = redisApiContext + WEB_SESSION_GET + "?key=" + key;
            LOGGER.trace("url = {}", url);

            try {
                result = restClient.get(url);
            } catch (IOException e) {
                LOGGER.error(e.getLocalizedMessage(), e);
            }
        } else if (storageSystem.equalsIgnoreCase(HTTP_SESSION)) {
            result = request.getSession().getAttribute(name);
        }
        return result;
    }

    public void remove(HttpServletRequest request, String name) {
        if (storageSystem.equalsIgnoreCase(REDIS)) {
            String redisApiContext = config.getString("redisApi.context");
            String url = redisApiContext + WEB_SESSION_REMOVE;
            LOGGER.trace("url = {}", url);

            String key = defaultNamespaceWebSessionKey(getCookieId(request), name);
            Map<String, String> parameter = new HashMap<String, String>();
            parameter.put("key", key);

            try {
                restClient.post(url, parameter);
            } catch (IOException e) {
                LOGGER.error(e.getLocalizedMessage(), e);
            }

        } else if (storageSystem.equalsIgnoreCase(HTTP_SESSION)) {
            request.getSession().removeAttribute(name);
        }
    }

    public void invalidate(HttpServletRequest request, HttpServletResponse response) {
        if (storageSystem.equalsIgnoreCase(REDIS)) {
            String redisApiContext = config.getString("redisApi.context");
            String url = redisApiContext + WEB_SESSION_REMOVE;
            LOGGER.trace("url = {}", url);

            String mainSessionKey = defaultNamespaceWebSessionKey(getCookieId(request), MAIN_SESSION);
            Map<String, String> parameterMainSession = new HashMap<String, String>();
            parameterMainSession.put("key", mainSessionKey);

            String loginUserKey = defaultNamespaceWebSessionKey(getCookieId(request), LOGIN_USER);
            Map<String, String> parameterLoginUser = new HashMap<String, String>();
            parameterLoginUser.put("key", loginUserKey);

            String menuListKey = defaultNamespaceWebSessionKey(getCookieId(request), MENU_LIST);
            Map<String, String> parameterMenuList = new HashMap<String, String>();
            parameterMenuList.put("key", menuListKey);

            String permissionListKey = defaultNamespaceWebSessionKey(getCookieId(request), USER_PERMISSION_LIST);
            Map<String, String> parameterPermissionList = new HashMap<String, String>();
            parameterPermissionList.put("key", permissionListKey);

            Cookie cookie = new Cookie(COOKIE_NAME, getCookieId(request));
            cookie.setDomain("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);

            try {
                restClient.post(url, parameterMainSession);
                restClient.post(url, parameterLoginUser);
                restClient.post(url, parameterMenuList);
                restClient.post(url, parameterPermissionList);
            } catch (IOException e) {
                LOGGER.error(e.getLocalizedMessage(), e);
            }

        } else if (storageSystem.equalsIgnoreCase(HTTP_SESSION)) {
            request.getSession().invalidate();
        }
    }

    public String getCookieId(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String cookieId = null;
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if (cookie.getName().equals(COOKIE_NAME)) {
                    cookieId = cookie.getValue();
                    break;
                }
            }
        }
        return cookieId;
    }

    private String defaultNamespaceWebSessionKey(String cookieId, String key) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(WEB_SESSION);
        stringBuilder.append(":");
        stringBuilder.append(cookieId);
        stringBuilder.append(":");
        stringBuilder.append(key);
        return stringBuilder.toString();
    }

}
