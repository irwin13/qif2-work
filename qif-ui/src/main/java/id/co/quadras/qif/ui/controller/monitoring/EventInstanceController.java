package id.co.quadras.qif.ui.controller.monitoring;

import id.co.quadras.qif.model.vo.message.QifMessageType;
import id.co.quadras.qif.ui.WebPage;
import id.co.quadras.qif.ui.dto.monitoring.EventInstance;
import id.co.quadras.qif.ui.dto.monitoring.EventMsg;
import id.co.quadras.qif.ui.service.monitoring.EventInstanceService;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.inject.Inject;
import com.irwin13.winwork.basic.model.PagingModel;
import com.irwin13.winwork.basic.utilities.PagingUtil;
import com.irwin13.winwork.basic.utilities.StringCompressor;

/**
 * @author irwin Timestamp : 08/07/2014 14:26
 */
@Path("/eventInstance")
public class EventInstanceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventInstanceController.class);
    private static final String PACKAGE_PAGE_PREFIX = "vita/monitoring/";

    @Context
    HttpServletRequest request;

    @Inject
    private WebPage webPage;

    @Inject
    private EventInstanceService service;

    @GET
    @Path("/list")
    @Produces(MediaType.TEXT_HTML)
    public Response list() {
        Map<String, Object> objectMap = webPage.mapWithLoginUser(request);
        objectMap.put("modelName", "eventInstance");
        String content = webPage.stringFromVm(PACKAGE_PAGE_PREFIX + "eventInstance_list.vm", objectMap);
        return webPage.okResponse(content);
    }

    @GET
    @Path("/listAjax")
    @Produces(MediaType.TEXT_HTML)
    public Response listAjax() {
        int pageStart = webPage.readParameterPageStart(request);
        int pageSize = webPage.readParameterPageSize(request);

        List<EventInstance> list = service.selectEventInstance(pageStart, pageSize);
        long total = service.selectCountEventInstance();

        PagingModel pagingModel = PagingUtil.getPagingModel(total, pageStart, pageSize);

        Map<String, Object> objectMap = webPage.mapWithLoginUser(request);
        objectMap.put("modelName", "eventInstance");
        objectMap.put("list", list);
        objectMap.put("pagingModel", pagingModel);
        objectMap.put("pageSize", pageSize);

        String content = webPage.stringFromVm(PACKAGE_PAGE_PREFIX + "eventInstance_listAjax.vm", objectMap);
        return webPage.okResponse(content);
    }

    @GET
    @Path("/viewMsgContent")
    @Produces(MediaType.TEXT_PLAIN)
    public Response viewMessageContent(@QueryParam("id") String id) {
        LOGGER.debug("eventLogId = {}", id);
        EventMsg eventMsg = service.getEventMsg(id);
        String content = "No content";
        if (eventMsg != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

            LOGGER.debug("eventMsg = {}", eventMsg);
            if (QifMessageType.STRING.getName().equalsIgnoreCase(eventMsg.getMsgType())) {
                content = StringEscapeUtils.escapeXml11(StringCompressor.decompress(eventMsg.getMessageContent()));
            } else if (QifMessageType.OBJECT.getName().equalsIgnoreCase(eventMsg.getMsgType())) {
                String decompress = StringCompressor.decompress(eventMsg.getMessageContent());
                try {
                    content = StringEscapeUtils.escapeXml11(objectMapper.writeValueAsString(decompress));
                } catch (JsonProcessingException e) {
                    LOGGER.error(e.getLocalizedMessage(), e);
                }
            } else {
                content = "Message in binary format";
            }
        }

        content = content.replaceAll("(\\\\r\\\\n|\\\\n)", "<br />");
        content = content.replaceAll("\\\\", "");
        content = content.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");

        return webPage.okResponse(content);
    }

}
