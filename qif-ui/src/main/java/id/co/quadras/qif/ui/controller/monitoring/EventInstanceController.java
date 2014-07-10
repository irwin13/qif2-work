package id.co.quadras.qif.ui.controller.monitoring;

import com.google.inject.Inject;
import com.irwin13.winwork.basic.model.PagingModel;
import com.irwin13.winwork.basic.utilities.PagingUtil;
import com.irwin13.winwork.basic.utilities.StringCompressor;
import id.co.quadras.qif.ui.WebPage;
import id.co.quadras.qif.ui.dto.monitoring.EventInstance;
import id.co.quadras.qif.ui.dto.monitoring.EventMsg;
import id.co.quadras.qif.ui.service.monitoring.EventInstanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

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
    public Response selectEventInstance() {
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

        String content = webPage.stringFromVm(PACKAGE_PAGE_PREFIX + "eventInstance_list.vm", objectMap);
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
            LOGGER.debug("eventMsg = {}", eventMsg);
            content = StringCompressor.decompress(eventMsg.getMessageContent());
        }
        return webPage.okResponse(content);
    }

}