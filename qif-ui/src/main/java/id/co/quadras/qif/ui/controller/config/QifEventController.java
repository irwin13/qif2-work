package id.co.quadras.qif.ui.controller.config;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.irwin13.winwork.basic.validator.AbstractValidator;
import com.irwin13.winwork.basic.validator.ValidationStatus;
import com.irwin13.winwork.basic.validator.ValidatorResult;
import id.co.quadras.qif.core.model.entity.QifEvent;
import id.co.quadras.qif.core.model.entity.QifEventProperty;
import id.co.quadras.qif.ui.WebPage;
import id.co.quadras.qif.ui.WebSession;
import id.co.quadras.qif.ui.controller.CrudController;
import id.co.quadras.qif.ui.service.config.QifEventService;
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
@Path("/" + QifEvent.MODEL_NAME)
public class QifEventController extends CrudController {

    private static final Logger LOGGER = LoggerFactory.getLogger(QifEventController.class);
    private static final String PACKAGE_PAGE_PREFIX = "vita/config/";

    @Inject
    private QifEventService qifEventService;

    @Inject
    @Named(QifEvent.MODEL_NAME)
    private AbstractValidator abstractValidator;

    @Inject
    private WebSession webSession;

    @Context
    HttpServletRequest request;

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
        webSession.remove(request, "qifEventPropertyList");
        return basicListPage(request, QifEvent.class, QifEvent.MODEL_NAME);
    }

    @GET
    @Path("/listAjax")
    @Produces(MediaType.TEXT_HTML)
    public Response ajaxListPage() {
        return basicListAjaxPage(request, qifEventService, QifEvent.MODEL_NAME, PACKAGE_PAGE_PREFIX);
    }

    @GET
    @Path("/detailAjax")
    @Produces(MediaType.TEXT_HTML)
    public Response detailPage(@QueryParam("id") String id) {
        LOGGER.debug("id = {}", id);
        return basicDetailPage(request, qifEventService, QifEvent.MODEL_NAME, PACKAGE_PAGE_PREFIX, id);
    }

    @GET
    @Path("/create")
    @Produces(MediaType.TEXT_HTML)
    public Response createPage() {
        return basicCreatePage(request, QifEvent.MODEL_NAME, PACKAGE_PAGE_PREFIX);
    }

    @GET
    @Path("/edit")
    @Produces(MediaType.TEXT_HTML)
    public Response editPage(@QueryParam("id") String id) {
        LOGGER.debug("id = {}", id);
        return basicEditPage(request, qifEventService, QifEvent.MODEL_NAME, PACKAGE_PAGE_PREFIX, id);
    }

    @GET
    @Path("/delete")
    @Produces(MediaType.TEXT_HTML)
    public Response delete(@QueryParam("id") String id) throws URISyntaxException {
        LOGGER.debug("id = {}", id);
        return basicDelete(request, qifEventService, QifEvent.MODEL_NAME, id);
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public Response create(MultivaluedMap<String, String> formMap) throws URISyntaxException {
        LOGGER.debug("formMap = {}", formMap);
        QifEvent model = new QifEvent();
        webPage.genericReadFormParameter(model, formMap);
        readAdditionalParameter(formMap, model);
        ValidatorResult<QifEvent> validatorResult = abstractValidator.onCreate(model, webPage.displayLang(request));

        if (validatorResult.getValidationStatus().equals(ValidationStatus.SUCCESS)) {
            webPage.setUserLogged(request, model, false);
            List<QifEventProperty> qifEventPropertyList = (List<QifEventProperty>) webSession.get(request, "qifEventPropertyList");
            model.setQifEventPropertyList(qifEventPropertyList);
            qifEventService.insert(model);
            webSession.remove(request, "qifEventPropertyList");
            return webPage.redirectListPage(QifEvent.MODEL_NAME);
        } else {
            Map<String, Object> objectMap = webPage.mapWithLoginUser(request);
            objectMap.put("modelName", QifEvent.MODEL_NAME);
            objectMap.put("model", model);
            objectMap.put("errorMessage", validatorResult.getFieldMessages());
            setReferenceData(objectMap);
            String content = webPage.stringFromVm(PACKAGE_PAGE_PREFIX + QifEvent.MODEL_NAME + WebPage.CREATE_PAGE_SUFFIX, objectMap);
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

        QifEvent model = qifEventService.getById(id, true);
        webPage.genericReadFormParameter(model, formMap);
        readAdditionalParameter(formMap, model);
        ValidatorResult<QifEvent> validatorResult = abstractValidator.onEdit(model, webPage.displayLang(request));

        if (validatorResult.getValidationStatus().equals(ValidationStatus.SUCCESS)) {
            webPage.setUserLogged(request, model, true);
            List<QifEventProperty> qifEventPropertyList = (List<QifEventProperty>) webSession.get(request, "qifEventPropertyList");
            model.setQifEventPropertyList(qifEventPropertyList);
            qifEventService.update(model);
            webSession.remove(request, "qifEventPropertyList");
            return webPage.redirectListPage(QifEvent.MODEL_NAME);
        } else {
            Map<String, Object> objectMap = webPage.mapWithLoginUser(request);
            objectMap.put("modelName", QifEvent.MODEL_NAME);
            objectMap.put("model", model);
            objectMap.put("errorMessage", validatorResult.getFieldMessages());
            setReferenceData(objectMap);
            String content = webPage.stringFromVm(PACKAGE_PAGE_PREFIX + QifEvent.MODEL_NAME + WebPage.EDIT_PAGE_SUFFIX, objectMap);
            return webPage.okResponse(content);
        }
    }

    @POST
    @Path("/qifEventProperty")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response qifEventProperty(MultivaluedMap<String, String> formMap) {

        String id = formMap.getFirst("id");

        LOGGER.debug("id = {}", id);
        QifEvent qifEvent = null;
        if (!Strings.isNullOrEmpty(id)) {
            qifEvent = qifEventService.getById(id, true);
            if (qifEvent == null) {
                throw new NullPointerException("FATAL ERROR :: QifEvent with id = " + id + " is missing in database");
            }
        }

        List<QifEventProperty> qifEventPropertyList = (List<QifEventProperty>) webSession.get(request, "qifEventPropertyList");
        if (qifEventPropertyList == null) {
            if (qifEvent != null && qifEvent.getQifEventPropertyList() != null) {
                qifEventPropertyList = qifEvent.getQifEventPropertyList();
            } else {
                qifEventPropertyList = new LinkedList<QifEventProperty>();
            }
        }

        QifEventProperty qifEventProperty = new QifEventProperty();
        Map<String, String> errors = new HashMap<String, String>();

        String qifEventPropertyAction = formMap.getFirst("qifEventPropertyAction");
        LOGGER.debug("qifEventPropertyAction = {}", qifEventPropertyAction);

        String indexString = formMap.getFirst("qifEventPropertyIndex");
        int index = -1;
        try {
            index = Integer.valueOf(indexString);
        } catch (NumberFormatException e) {}

        LOGGER.debug("index = {}", index);

        if (!Strings.isNullOrEmpty(qifEventPropertyAction)) {
            if (qifEventPropertyAction.equals("add")) {
                readParameterQifEventProperty(formMap, qifEventProperty, errors);
                if (errors.size() <= 0) {
                    if (index == -1) {
                        qifEventPropertyList.add(qifEventProperty);
                    } else {
                        qifEventProperty = qifEventPropertyList.get(index);
                        if (qifEventProperty != null) {
                            readParameterQifEventProperty(formMap, qifEventProperty, errors);
                        }
                    }
                }
            } else if (qifEventPropertyAction.equals("edit")) {
                qifEventProperty = qifEventPropertyList.get(index);
            } else if (qifEventPropertyAction.equals("remove")) {
                qifEventPropertyList.remove(index);
            }
        }

        webSession.set(request, "qifEventPropertyList", qifEventPropertyList);

        Map<String, Object> objectMap = webPage.mapWithLoginUser(request);
        objectMap.put("qifEventProperty", qifEventProperty);
        objectMap.put("model", qifEvent);
        objectMap.put("qifEventPropertyList", qifEventPropertyList);
        objectMap.put("errors", errors);

        if (Strings.isNullOrEmpty(qifEventPropertyAction) ||
                (errors.size() <= 0 && qifEventPropertyAction.equalsIgnoreCase("add"))) {

            objectMap.put("isCleanError", true);
        } else {
            objectMap.put("isCleanError", false);
        }

        String content = webPage.stringFromVm(PACKAGE_PAGE_PREFIX + "qifEvent_child_qifEventProperty.vm", objectMap);
        return webPage.okResponse(content);
    }

    private void readParameterQifEventProperty(MultivaluedMap<String, String> formMap, QifEventProperty qifEventProperty,
                                                Map<String, String> errors) {

        String propertyKey = formMap.getFirst("qifEventProperty_propertyKey");
        LOGGER.debug("propertyKey = {}", propertyKey);
        if (Strings.isNullOrEmpty(propertyKey)) {
            errors.put("propertyKey", "Property Key empty");
        } else {
            qifEventProperty.setPropertyKey(propertyKey);
        }

        String propertyValue = formMap.getFirst("qifEventProperty_propertyValue");
        LOGGER.debug("propertyValue = {}", propertyValue);
        if (Strings.isNullOrEmpty(propertyValue)) {
            errors.put("propertyValue", "Property Value empty");
        } else {
            qifEventProperty.setPropertyValue(propertyValue);
        }

        String description = formMap.getFirst("qifEventProperty_description");
        LOGGER.debug("description = {}", description);
        qifEventProperty.setDescription(description);
    }

}
