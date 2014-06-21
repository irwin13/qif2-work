package id.co.quadras.qif.ui;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.irwin13.winwork.basic.WinWorkConstants;
import com.irwin13.winwork.basic.annotations.FormReadExclude;
import com.irwin13.winwork.basic.config.WinWorkConfig;
import com.irwin13.winwork.basic.exception.WinWorkException;
import com.irwin13.winwork.basic.model.KeyValue;
import com.irwin13.winwork.basic.model.SortParameter;
import com.irwin13.winwork.basic.model.entity.WinWorkBasicEntity;
import com.irwin13.winwork.basic.model.entity.app.AppUser;
import com.irwin13.winwork.basic.utilities.PojoUtil;
import com.irwin13.winwork.basic.utilities.StringUtil;
import com.irwin13.winwork.basic.utilities.WinWorkVelocityUtil;
import id.co.quadras.qif.ui.dao.EntityResolver;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.*;

/**
 * @author irwin Timestamp : 12/04/13 16:46
 */
public class WebPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebPage.class);

    public static final int DEFAULT_PAGE_SIZE = 10;

    public static final String COMMON_PAGE_PACKAGE = "vita/common/";
    public static final String ERROR_PAGE_PACKAGE = "vita/error/";

    public static final String GENERIC_LIST_PAGE= "list_page.vm";

    public static final String LIST_PAGE_SUFFIX = "_list.vm";
    public static final String LIST_AJAX_PAGE_SUFFIX = "_listAjax.vm";

    public static final String DETAIL_PAGE_SUFFIX = "_detail.vm";
    public static final String CREATE_PAGE_SUFFIX = "_create.vm";
    public static final String EDIT_PAGE_SUFFIX = "_edit.vm";

    private static final Date EXPIRE_DATE = new Date(0L); // set expires on the beginning of time
    private static final String NO_CACHE = "no-cache, no-store, must-revalidate"; // HTTP 1.1
    private static final String PRAGMA = "Pragma"; // HTTP 1.0
    private static final String PRAGMA_NO_CACHE = "no-cache"; // HTTP 1.0

    private static final String CONTENT_LENGTH = "Content-Length";

    @Inject
    private WebSession webSession;

    @Inject
    private WinWorkVelocityUtil velocityUtil;

    @Inject
    private EntityResolver entityResolver;

    @Inject
    private WinWorkConfig configuration;

    public Response okResponseNoCache(String content) {

        CacheControl cacheControl = new CacheControl();
        cacheControl.setNoCache(true);
        cacheControl.setNoStore(true);
        cacheControl.setMaxAge(0);
        cacheControl.setMustRevalidate(true);

        return Response.ok(content)
                .header(CONTENT_LENGTH, content.length())
                .expires(EXPIRE_DATE)
                .cacheControl(cacheControl)
                .header(PRAGMA, PRAGMA_NO_CACHE)
                .build();
    }

    public Response okResponse(String content) {
        return Response.ok(content).header(CONTENT_LENGTH, content.length()).build();
    }

    public Response errorResponse(String errorMessage) {
        return Response.serverError()
                .entity(errorMessage)
                .header(CONTENT_LENGTH, errorMessage.length())
                .build();
    }

    public String stringFromVm(String vmName, Map<String, Object> objectMap) {
        return velocityUtil.stringFromVm(vmName, objectMap);
    }

    public AppUser loginUser(HttpServletRequest request) {
        AppUser appUser = (AppUser) webSession.get(request, WebSession.LOGIN_USER);
        if (appUser == null) {
            throw new WinWorkException("Unexpected error, null AppUser where it's should be exists");
        }
        return appUser;
    }

    public String displayLang(HttpServletRequest request) {
        AppUser appUser = loginUser(request);
        if (appUser != null && !Strings.isNullOrEmpty(appUser.getDisplayLang())) {
            return appUser.getDisplayLang();
        } else {
            return WinWorkConstants.DEFAULT_LANG;
        }
    }

    public Map<String, Object> mapWithLoginUser(HttpServletRequest request) {
        Map<String, Object> objectMap = new HashMap<String, Object>();
        objectMap.put(WebSession.LOGIN_USER, loginUser(request));
        return objectMap;
    }

    public Response redirectResponse(String uri) throws URISyntaxException {
        return Response.temporaryRedirect(new URI(uri)).status(Response.Status.SEE_OTHER).build();
    }

    public Response redirectListPage(String modelName) throws URISyntaxException {
        String webContext = configuration.getString("web.context");
        LOGGER.debug("webContext to redirect = {}", webContext);
        return redirectResponse(webContext + "/" + modelName + "/list");
    }

    public SortParameter readParameterSort(HttpServletRequest request) {

        String sortMethod = request.getParameter("sortMethod");
        String sortProperty = request.getParameter("sortProperty");
        if (Strings.isNullOrEmpty(sortProperty)) sortProperty = WinWorkConstants.DEFAULT_PROPERTY_ORDER;

        return new SortParameter(sortProperty, setOrderMethod(sortMethod));
    }

    public String readParameterSearchKeyword(HttpServletRequest request) {
        String search = request.getParameter("searchKeyword");
        search = StringUtil.setSearchKeyword(search);
        return search;
    }

    public int readParameterPageStart(HttpServletRequest request) {
        int result = 0;
        String param = request.getParameter("pageStart");
        try {
            result = Integer.valueOf(param);
        } catch (NumberFormatException e) {}
        return result;
    }

    public int readParameterPageSize(HttpServletRequest request) {
        int result = DEFAULT_PAGE_SIZE;
        String param = request.getParameter("pageSize");
        try {
            result = Integer.valueOf(param);
        } catch (NumberFormatException e) {}
        return result;
    }

    private String setOrderMethod(String input) {
        if (Strings.isNullOrEmpty(input)) {
            return SortParameter.DESC;
        } else {
            if (input.equalsIgnoreCase(SortParameter.ASC)) {
                return SortParameter.ASC;
            } else {
                return SortParameter.DESC;
            }
        }
    }

    public List<KeyValue> sortableField(Class<?> clazz) {
        List<KeyValue> result = new LinkedList<KeyValue>();
        Map<String, Class<?>> sortableMap = PojoUtil.getSortableField(clazz);
        for (Map.Entry<String, Class<?>> entry : sortableMap.entrySet()) {
            String display = entry.getKey();
            if (display.contains(".")) {
                display = display.substring(display.lastIndexOf(".") + 1, display.length());
            }
            KeyValue keyValue = new KeyValue(entry.getKey(), StringUtil.insertStringInCamelCase(true, display, " "));
            result.add(keyValue);
        }
        return result;
    }

    public void genericReadFormParameter(Object model, MultivaluedMap formMap) {
        if (model != null) {
            Field[] fieldArray = model.getClass().getDeclaredFields();
            for (Field field : fieldArray) {
                String fieldName = field.getName();
                Class<?> fieldType = field.getType();

                boolean readField = true;

                Annotation[] annotationArray = field.getAnnotations();
                annotationScan:
                for (int a = 0; a < annotationArray.length; a++) {
                    Annotation annotation = annotationArray[a];
                    if (annotation.annotationType().equals(FormReadExclude.class)) {
                        readField = false;
                        break annotationScan;
                    }
                }

                if (readField) {
                    String paramValue = (String) formMap.getFirst(fieldName);
                    try {
                        if (fieldType.equals(String.class)) {
                            PropertyUtils.setProperty(model, fieldName, paramValue);
                            LOGGER.debug("set field {} with value = {}", fieldName, paramValue);
                        } else if (fieldType.equals(Integer.class)) {
                            Integer value = null;
                            if (!Strings.isNullOrEmpty(paramValue)) {
                                value = Integer.valueOf(paramValue);
                            }
                            PropertyUtils.setProperty(model, fieldName, value);
                            LOGGER.debug("set field {} with value = {}", fieldName, paramValue);
                        } else if (fieldType.equals(Long.class)) {
                            Long value = null;
                            if (!Strings.isNullOrEmpty(paramValue)) {
                                value = Long.valueOf(paramValue);
                            }
                            PropertyUtils.setProperty(model, fieldName, value);
                            LOGGER.debug("set field {} with value = {}", fieldName, paramValue);
                        } else if (fieldType.equals(Float.class)) {
                            Float value = null;
                            if (!Strings.isNullOrEmpty(paramValue)) {
                                value = Float.valueOf(paramValue);
                            }
                            PropertyUtils.setProperty(model, fieldName, value);
                            LOGGER.debug("set field {} with value = {}", fieldName, paramValue);
                        } else if (fieldType.equals(Double.class)) {
                            Double value = null;
                            if (!Strings.isNullOrEmpty(paramValue)) {
                                value = Double.valueOf(paramValue);
                            }
                            PropertyUtils.setProperty(model, fieldName, value);
                            LOGGER.debug("set field {} with value = {}", fieldName, paramValue);
                        } else if (fieldType.equals(Date.class)) {
                            if (!Strings.isNullOrEmpty(paramValue)) {
                                Date date = WinWorkConstants.SDF_DEFAULT.parse(paramValue);
                                if (date != null) {
                                    PropertyUtils.setProperty(model, fieldName, date);
                                    LOGGER.debug("set field {} with value = {}", fieldName, date);
                                }
                            } else {
                                PropertyUtils.setProperty(model, fieldName, null);
                            }
                        } else if (fieldType.equals(Boolean.class)) {
                            if (!Strings.isNullOrEmpty(paramValue)) {
                                PropertyUtils.setProperty(model, fieldName, Boolean.TRUE);
                                LOGGER.debug("set field {} with value = {}", fieldName, Boolean.TRUE);
                            } else {
                                PropertyUtils.setProperty(model, fieldName, Boolean.FALSE);
                                LOGGER.debug("set field {} with value = {}", fieldName, Boolean.FALSE);
                            }
                        } else if (WinWorkBasicEntity.class.isAssignableFrom(fieldType)) {
                            if (!Strings.isNullOrEmpty(paramValue)) {
                                LOGGER.debug("set field {} with type {} using id = {}",
                                        new Object[]{fieldName, fieldType, paramValue});
                                Object bean = entityResolver.getById(paramValue, fieldType);
                                if (model != null) {
                                    PropertyUtils.setProperty(model, fieldName, bean);
                                    LOGGER.debug("set field {} with type {} success", fieldName, fieldType);
                                }
                            } else {
                                PropertyUtils.setProperty(model, fieldName, null);
                            }
                        }
                    } catch (IllegalAccessException e) {
                        LOGGER.error(e.getLocalizedMessage(), e);
                    } catch (InvocationTargetException e) {
                        LOGGER.error(e.getLocalizedMessage(), e);
                    } catch (NoSuchMethodException e) {
                        LOGGER.error(e.getLocalizedMessage(), e);
                    } catch (ParseException e) {
                        LOGGER.error(e.getLocalizedMessage(), e);
                    } catch (NumberFormatException e) {
                        LOGGER.error(e.getLocalizedMessage(), e);
                    }
                }
            }
        } else {
            throw new NullPointerException("model is null, please fix your code");
        }
    }

    public void setUserLogged(HttpServletRequest request, WinWorkBasicEntity bean, boolean isOnlyUpdate) {
        AppUser user = loginUser(request);
        if (user != null) {
            if (isOnlyUpdate) bean.setLastUpdateBy(user.getUsername());
            else {
                bean.setCreateBy(user.getUsername());
                bean.setLastUpdateBy(user.getUsername());
            }
        } else {
            if (isOnlyUpdate) bean.setCreateBy(WinWorkConstants.USER_SYSTEM);
            else {
                bean.setCreateBy(WinWorkConstants.USER_SYSTEM);
                bean.setLastUpdateBy(WinWorkConstants.USER_SYSTEM);
            }
        }
    }
}
