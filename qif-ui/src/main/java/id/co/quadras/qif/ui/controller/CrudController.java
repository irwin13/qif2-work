package id.co.quadras.qif.ui.controller;

import com.google.inject.Inject;
import com.irwin13.winwork.basic.model.KeyValue;
import com.irwin13.winwork.basic.model.PagingModel;
import com.irwin13.winwork.basic.model.SearchParameter;
import com.irwin13.winwork.basic.model.SortParameter;
import com.irwin13.winwork.basic.model.entity.WinWorkBasicEntity;
import com.irwin13.winwork.basic.service.WinWorkService;
import com.irwin13.winwork.basic.utilities.PagingUtil;
import com.irwin13.winwork.basic.validator.AbstractValidator;
import com.irwin13.winwork.basic.validator.ValidationStatus;
import com.irwin13.winwork.basic.validator.ValidatorResult;
import id.co.quadras.qif.ui.WebPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

/**
 * @author irwin Timestamp : 23/04/13 18:03
 *
 *
 */
public abstract class CrudController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrudController.class);

    public static final String SELECT = "/select";
    public static final String SELECT_PAGED = "/select";
    public static final String SELECT_COUNT = "/selectCount";

    public static final String SELECT_SEARCH = "/selectSearch";
    public static final String SELECT_SEARCH_PAGED = "/selectSearch";
    public static final String SELECT_SEARCH_COUNT = "/selectSearchCount";

    public static final String INSERT = "/insert";
    public static final String UPDATE = "/update";
    public static final String DELETE = "/delete";
    public static final String SOFT_DELETE = "/softDelete";
    public static final String INSERT_OR_UPDATE = "/insertOrUpdate";

    @Inject
    protected WebPage webPage;

    protected abstract void setReferenceData(Map<String, Object> objectMap);
    protected abstract void readAdditionalParameter(MultivaluedMap<String, String> formMap, Object model);

    public Response basicListPage(HttpServletRequest request, Class<?> modelClass, String modelName) {
        List<KeyValue> keyValueList = webPage.sortableField(modelClass);
        Map<String, Object> objectMap = webPage.mapWithLoginUser(request);
        objectMap.put("modelName", modelName);
        objectMap.put("propertyList", keyValueList);
        String content = webPage.stringFromVm(WebPage.COMMON_PAGE_PACKAGE + WebPage.GENERIC_LIST_PAGE, objectMap);
        return webPage.okResponse(content);
    }

    public Response basicListAjaxPage(HttpServletRequest request, WinWorkService service, String modelName,
                                      String packageName) {

        String searchKeyword = webPage.readParameterSearchKeyword(request);
        SortParameter sortParameter = webPage.readParameterSort(request);
        int pageStart = webPage.readParameterPageStart(request);
        int pageSize = webPage.readParameterPageSize(request);

        SearchParameter searchParameter = new SearchParameter(searchKeyword, sortParameter.getColumnName(), sortParameter.getSortMethod());
        List<? extends WinWorkBasicEntity> list = service.selectSearch(searchParameter, pageStart, pageSize);
        long total = service.selectSearchCount(searchKeyword);

        PagingModel pagingModel = PagingUtil.getPagingModel(total, pageStart, pageSize);

        Map<String, Object> objectMap = webPage.mapWithLoginUser(request);
        objectMap.put("list", list);
        objectMap.put("pagingModel", pagingModel);
        objectMap.put("pageSize", pageSize);
        objectMap.put("modelName", modelName);

        String content = webPage.stringFromVm(packageName + modelName + WebPage.LIST_AJAX_PAGE_SUFFIX, objectMap);

        return webPage.okResponse(content);

    }

    public Response basicDetailPage(HttpServletRequest request, WinWorkService service,
                                    String modelName, String packageName, String id) {

        WinWorkBasicEntity model = service.getById(id, true);

        Map<String, Object> objectMap = webPage.mapWithLoginUser(request);
        objectMap.put("model", model);
        objectMap.put("modelName", modelName);
        String content = webPage.stringFromVm(packageName + modelName + WebPage.DETAIL_PAGE_SUFFIX, objectMap);

        return webPage.okResponse(content);
    }

    public Response basicCreatePage(HttpServletRequest request, String modelName, String packageName) {
        Map<String, Object> objectMap = webPage.mapWithLoginUser(request);
        objectMap.put("modelName", modelName);
        setReferenceData(objectMap);
        String content = webPage.stringFromVm(packageName + modelName + WebPage.CREATE_PAGE_SUFFIX, objectMap);
        return webPage.okResponse(content);
    }

    public Response basicEditPage(HttpServletRequest request, WinWorkService service,
                                  String modelName, String packageName, String id) {

        WinWorkBasicEntity model = service.getById(id, true);

        Map<String, Object> objectMap = webPage.mapWithLoginUser(request);
        objectMap.put("model", model);
        objectMap.put("modelName", modelName);
        setReferenceData(objectMap);
        String content = webPage.stringFromVm(packageName + modelName + WebPage.EDIT_PAGE_SUFFIX, objectMap);

        return webPage.okResponse(content);
    }

    public Response basicDelete(HttpServletRequest request, WinWorkService service, String modelName, String id) throws URISyntaxException {
        WinWorkBasicEntity model = service.getById(id, true);
        if (model != null) {
            webPage.setUserLogged(request, model, true);
            service.softDelete(model);
        }
        return webPage.redirectListPage(modelName);
    }

    public Response basicCreate(HttpServletRequest request, WinWorkService service,
                                Class<? extends WinWorkBasicEntity> modelClass,
                                String modelName, String packageName,
                                MultivaluedMap<String, String> formMap, AbstractValidator validator)
            throws InstantiationException, IllegalAccessException, URISyntaxException {

        LOGGER.debug("formMap = {}", formMap);
        WinWorkBasicEntity model = modelClass.newInstance();
        webPage.genericReadFormParameter(model, formMap);
        readAdditionalParameter(formMap, model);
        ValidatorResult<? extends WinWorkBasicEntity> validatorResult = validator.onCreate(model, webPage.displayLang(request));

        if (validatorResult.getValidationStatus().equals(ValidationStatus.SUCCESS)) {
            webPage.setUserLogged(request, model, false);
            service.insert(model);
            return webPage.redirectListPage(modelName);
        } else {
            Map<String, Object> objectMap = webPage.mapWithLoginUser(request);
            objectMap.put("modelName", modelName);
            objectMap.put("model", model);
            objectMap.put("errorMessage", validatorResult.getFieldMessages());
            setReferenceData(objectMap);
            String content = webPage.stringFromVm(packageName + modelName + WebPage.CREATE_PAGE_SUFFIX, objectMap);
            return webPage.okResponse(content);
        }
    }

    public Response basicEdit(HttpServletRequest request, WinWorkService service,
                              String modelName, String packageName,
                              MultivaluedMap<String, String> formMap, AbstractValidator validator)
            throws URISyntaxException {

        LOGGER.debug("formMap = {}", formMap);
        String id = formMap.getFirst("id");

        WinWorkBasicEntity model = service.getById(id, true);
        webPage.genericReadFormParameter(model, formMap);
        readAdditionalParameter(formMap, model);
        ValidatorResult<? extends WinWorkBasicEntity> validatorResult = validator.onEdit(model, webPage.displayLang(request));

        if (validatorResult.getValidationStatus().equals(ValidationStatus.SUCCESS)) {
            webPage.setUserLogged(request, model, true);
            service.update(model);
            return webPage.redirectListPage(modelName);
        } else {
            Map<String, Object> objectMap = webPage.mapWithLoginUser(request);
            objectMap.put("modelName", modelName);
            objectMap.put("model", model);
            objectMap.put("errorMessage", validatorResult.getFieldMessages());
            setReferenceData(objectMap);
            String content = webPage.stringFromVm(packageName + modelName + WebPage.EDIT_PAGE_SUFFIX, objectMap);
            return webPage.okResponse(content);
        }
    }
}
