package id.co.quadras.qif.ui.controller.config;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.irwin13.winwork.basic.validator.AbstractValidator;
import com.irwin13.winwork.basic.validator.ValidationStatus;
import com.irwin13.winwork.basic.validator.ValidatorResult;
import id.co.quadras.qif.core.model.entity.QifAdapter;
import id.co.quadras.qif.core.model.entity.QifAdapterProperty;
import id.co.quadras.qif.core.model.vo.adapter.AdapterInterface;
import id.co.quadras.qif.core.model.vo.event.EventType;
import id.co.quadras.qif.ui.WebPage;
import id.co.quadras.qif.ui.WebSession;
import id.co.quadras.qif.ui.controller.CrudController;
import id.co.quadras.qif.ui.service.config.QifAdapterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author irwin Timestamp : 02/07/2014 14:21
 */
@Path("/" + QifAdapter.MODEL_NAME)
public class QifAdapterController extends CrudController {

    private static final Logger LOGGER = LoggerFactory.getLogger(QifAdapterController.class);
    private static final String PACKAGE_PAGE_PREFIX = "vita/config/";

    @Inject
    private QifAdapterService qifAdapterService;

    @Inject
    @Named(QifAdapter.MODEL_NAME)
    private AbstractValidator abstractValidator;

    @Inject
    private WebSession webSession;

    @Context
    HttpServletRequest request;

    @Override
    protected void setReferenceData(Map<String, Object> objectMap) {
        List<String> adapterInterfaceList = new LinkedList<String>();
        for (AdapterInterface adapterInterface : AdapterInterface.values()) {
            adapterInterfaceList.add(adapterInterface.getName());
        }
        objectMap.put("adapterInterfaceList", adapterInterfaceList);
    }

    @Override
    protected void readAdditionalParameter(MultivaluedMap<String, String> formMap, Object model) {
    }

    @GET
    @Path("/list")
    @Produces(MediaType.TEXT_HTML)
    public Response listPage() {
        webSession.remove(request, "qifAdapterPropertyList");
        return basicListPage(request, QifAdapter.class, QifAdapter.MODEL_NAME);
    }

    @GET
    @Path("/listAjax")
    @Produces(MediaType.TEXT_HTML)
    public Response ajaxListPage() {
        return basicListAjaxPage(request, qifAdapterService, QifAdapter.MODEL_NAME, PACKAGE_PAGE_PREFIX);
    }

    @GET
    @Path("/detailAjax")
    @Produces(MediaType.TEXT_HTML)
    public Response detailPage(@QueryParam("id") String id) {
        LOGGER.debug("id = {}", id);
        return basicDetailPage(request, qifAdapterService, QifAdapter.MODEL_NAME, PACKAGE_PAGE_PREFIX, id);
    }

    @GET
    @Path("/create")
    @Produces(MediaType.TEXT_HTML)
    public Response createPage() {
        return basicCreatePage(request, QifAdapter.MODEL_NAME, PACKAGE_PAGE_PREFIX);
    }

    @GET
    @Path("/edit")
    @Produces(MediaType.TEXT_HTML)
    public Response editPage(@QueryParam("id") String id) {
        LOGGER.debug("id = {}", id);
        return basicEditPage(request, qifAdapterService, QifAdapter.MODEL_NAME, PACKAGE_PAGE_PREFIX, id);
    }

    @GET
    @Path("/delete")
    @Produces(MediaType.TEXT_HTML)
    public Response delete(@QueryParam("id") String id) throws URISyntaxException {
        LOGGER.debug("id = {}", id);
        return basicDelete(request, qifAdapterService, QifAdapter.MODEL_NAME, id);
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public Response create(MultivaluedMap<String, String> formMap) throws URISyntaxException {
        LOGGER.debug("formMap = {}", formMap);
        QifAdapter model = new QifAdapter();
        webPage.genericReadFormParameter(model, formMap);
        readAdditionalParameter(formMap, model);
        ValidatorResult<QifAdapter> validatorResult = abstractValidator.onCreate(model, webPage.displayLang(request));

        if (validatorResult.getValidationStatus().equals(ValidationStatus.SUCCESS)) {
            webPage.setUserLogged(request, model, false);
            List<QifAdapterProperty> qifAdapterPropertyList = (List<QifAdapterProperty>) webSession.get(request, "qifAdapterPropertyList");
            model.setQifAdapterPropertyList(qifAdapterPropertyList);
            qifAdapterService.insert(model);
            webSession.remove(request, "qifAdapterPropertyList");
            return webPage.redirectListPage(QifAdapter.MODEL_NAME);
        } else {
            Map<String, Object> objectMap = webPage.mapWithLoginUser(request);
            objectMap.put("modelName", QifAdapter.MODEL_NAME);
            objectMap.put("model", model);
            objectMap.put("errorMessage", validatorResult.getFieldMessages());
            setReferenceData(objectMap);
            String content = webPage.stringFromVm(PACKAGE_PAGE_PREFIX + QifAdapter.MODEL_NAME + WebPage.CREATE_PAGE_SUFFIX, objectMap);
            return webPage.okResponse(content);
        }
    }

    @POST
    @Path("/edit")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public Response edit(MultivaluedMap<String, String> formMap) throws URISyntaxException {
        LOGGER.debug("formMap = {}", formMap);
        String id = formMap.getFirst("id");

        QifAdapter model = qifAdapterService.getById(id, true);
        webPage.genericReadFormParameter(model, formMap);
        readAdditionalParameter(formMap, model);
        ValidatorResult<QifAdapter> validatorResult = abstractValidator.onEdit(model, webPage.displayLang(request));

        if (validatorResult.getValidationStatus().equals(ValidationStatus.SUCCESS)) {
            webPage.setUserLogged(request, model, true);
            List<QifAdapterProperty> qifAdapterPropertyList = (List<QifAdapterProperty>) webSession.get(request, "qifAdapterPropertyList");
            model.setQifAdapterPropertyList(qifAdapterPropertyList);
            qifAdapterService.update(model);
            webSession.remove(request, "qifAdapterPropertyList");
            return webPage.redirectListPage(QifAdapter.MODEL_NAME);
        } else {
            Map<String, Object> objectMap = webPage.mapWithLoginUser(request);
            objectMap.put("modelName", QifAdapter.MODEL_NAME);
            objectMap.put("model", model);
            objectMap.put("errorMessage", validatorResult.getFieldMessages());
            setReferenceData(objectMap);
            String content = webPage.stringFromVm(PACKAGE_PAGE_PREFIX + QifAdapter.MODEL_NAME + WebPage.EDIT_PAGE_SUFFIX, objectMap);
            return webPage.okResponse(content);
        }
    }

    @POST
    @Path("/qifAdapterProperty")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response qifAdapterProperty(MultivaluedMap<String, String> formMap) {

        String id = formMap.getFirst("id");

        LOGGER.debug("id = {}", id);
        QifAdapter qifAdapter = null;
        if (!Strings.isNullOrEmpty(id)) {
            qifAdapter = qifAdapterService.getById(id, true);
            if (qifAdapter == null) {
                throw new NullPointerException("FATAL ERROR :: QifAdapter with id = " + id + " is missing in database");
            }
        }

        List<QifAdapterProperty> qifAdapterPropertyList = (List<QifAdapterProperty>) webSession.get(request, "qifAdapterPropertyList");
        if (qifAdapterPropertyList == null) {
            if (qifAdapter != null && qifAdapter.getQifAdapterPropertyList() != null) {
                qifAdapterPropertyList = qifAdapter.getQifAdapterPropertyList();
            } else {
                qifAdapterPropertyList = new LinkedList<QifAdapterProperty>();
            }
        }

        QifAdapterProperty qifAdapterProperty = new QifAdapterProperty();
        Map<String, String> errors = new HashMap<String, String>();

        String qifAdapterPropertyAction = formMap.getFirst("qifAdapterPropertyAction");
        LOGGER.debug("qifAdapterPropertyAction = {}", qifAdapterPropertyAction);

        String indexString = formMap.getFirst("qifAdapterPropertyIndex");
        int index = -1;
        try {
            index = Integer.valueOf(indexString);
        } catch (NumberFormatException e) {}

        LOGGER.debug("index = {}", index);

        if (!Strings.isNullOrEmpty(qifAdapterPropertyAction)) {
            if (qifAdapterPropertyAction.equals("add")) {
                readParameterQifAdapterProperty(formMap, qifAdapterProperty, errors);
                if (errors.size() <= 0) {
                    if (index == -1) {
                        qifAdapterPropertyList.add(qifAdapterProperty);
                    } else {
                        qifAdapterProperty = qifAdapterPropertyList.get(index);
                        if (qifAdapterProperty != null) {
                            readParameterQifAdapterProperty(formMap, qifAdapterProperty, errors);
                        }
                    }
                }
            } else if (qifAdapterPropertyAction.equals("edit")) {
                qifAdapterProperty = qifAdapterPropertyList.get(index);
            } else if (qifAdapterPropertyAction.equals("remove")) {
                qifAdapterPropertyList.remove(index);
            }
        }

        webSession.set(request, "qifAdapterPropertyList", qifAdapterPropertyList);

        Map<String, Object> objectMap = webPage.mapWithLoginUser(request);
        objectMap.put("qifAdapterProperty", qifAdapterProperty);
        objectMap.put("model", qifAdapter);
        objectMap.put("qifAdapterPropertyList", qifAdapterPropertyList);
        objectMap.put("errors", errors);

        if (Strings.isNullOrEmpty(qifAdapterPropertyAction) ||
                (errors.size() <= 0 && qifAdapterPropertyAction.equalsIgnoreCase("add"))) {

            objectMap.put("isCleanError", true);
        } else {
            objectMap.put("isCleanError", false);
        }

        String content = webPage.stringFromVm(PACKAGE_PAGE_PREFIX + "qifAdapter_child_qifAdapterProperty.vm", objectMap);
        return webPage.okResponse(content);
    }

    private void readParameterQifAdapterProperty(MultivaluedMap<String, String> formMap, QifAdapterProperty qifAdapterProperty,
                                               Map<String, String> errors) {

        String propertyKey = formMap.getFirst("qifAdapterProperty_propertyKey");
        LOGGER.debug("propertyKey = {}", propertyKey);
        if (Strings.isNullOrEmpty(propertyKey)) {
            errors.put("propertyKey", "Property Key empty");
        } else {
            qifAdapterProperty.setPropertyKey(propertyKey);
        }

        String propertyValue = formMap.getFirst("qifAdapterProperty_propertyValue");
        LOGGER.debug("propertyValue = {}", propertyValue);
        if (Strings.isNullOrEmpty(propertyValue)) {
            errors.put("propertyValue", "Property Value empty");
        } else {
            qifAdapterProperty.setPropertyValue(propertyValue);
        }

        String description = formMap.getFirst("qifAdapterProperty_description");
        LOGGER.debug("description = {}", description);
        qifAdapterProperty.setDescription(description);
    }

}
