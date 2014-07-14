package id.co.quadras.qif.ui.controller.monitoring;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.irwin13.winwork.basic.model.PagingModel;
import com.irwin13.winwork.basic.utilities.PagingUtil;
import com.irwin13.winwork.basic.utilities.StringCompressor;
import id.co.quadras.qif.core.model.vo.message.QifMessageType;
import id.co.quadras.qif.ui.WebPage;
import id.co.quadras.qif.ui.dto.monitoring.ProcessInstance;
import id.co.quadras.qif.ui.dto.monitoring.ProcessInstanceData;
import id.co.quadras.qif.ui.dto.monitoring.TaskInputMsg;
import id.co.quadras.qif.ui.dto.monitoring.TaskOutputMsg;
import id.co.quadras.qif.ui.service.monitoring.ProcessInstanceService;
import org.apache.commons.lang3.StringEscapeUtils;
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
 * @author irwin Timestamp : 08/07/2014 17:36
 */
@Path("/processInstance")
public class ProcessInstanceController {

    private static final String PACKAGE_PAGE_PREFIX = "vita/monitoring/";
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessInstanceController.class);

    @Context
    HttpServletRequest request;

    @Inject
    private WebPage webPage;

    @Inject
    private ProcessInstanceService service;

    @GET
    @Path("/list")
    @Produces(MediaType.TEXT_HTML)
    public Response selectProcessInstance() {
        int pageStart = webPage.readParameterPageStart(request);
        int pageSize = webPage.readParameterPageSize(request);

        List<ProcessInstance> list = service.selectProcessInstance(pageStart, pageSize);
        long total = service.selectCountProcessInstance();

        PagingModel pagingModel = PagingUtil.getPagingModel(total, pageStart, pageSize);

        Map<String, Object> objectMap = webPage.mapWithLoginUser(request);
        objectMap.put("modelName", "processInstance");
        objectMap.put("list", list);
        objectMap.put("pagingModel", pagingModel);
        objectMap.put("pageSize", pageSize);

        String content = webPage.stringFromVm(PACKAGE_PAGE_PREFIX + "processInstance_list.vm", objectMap);
        return webPage.okResponse(content);
    }

    @GET
    @Path("/detail")
    @Produces(MediaType.TEXT_HTML)
    public Response detailProcessInstance(@QueryParam("id") String id) {
        List<ProcessInstance> list = service.selectProcessTasks(id);
        Map<String, Object> objectMap = webPage.mapWithLoginUser(request);
        objectMap.put("modelName", "processInstance");
        objectMap.put("list", list);

        String content = webPage.stringFromVm(PACKAGE_PAGE_PREFIX + "processInstance_detail.vm", objectMap);
        return webPage.okResponse(content);
    }

    @GET
    @Path("/taskMsgContent")
    @Produces(MediaType.TEXT_HTML)
    public Response taskMessageContent(@QueryParam("id") String id) {
        String inputContentTitle = "<strong>Input Content</strong><br />";
        String outputContentTitle = "<strong>Output Content</strong><br />";

        String inputContent = "No content";
        String outputContent = "No content";

        TaskInputMsg taskInputMsg = service.getTaskInputMsg(id);
        if (taskInputMsg != null) {
            LOGGER.debug("taskInputMsg = {}", taskInputMsg);
            if (QifMessageType.TEXT.getName().equalsIgnoreCase(taskInputMsg.getMsgType())) {
                inputContent = StringEscapeUtils.escapeXml11(StringCompressor.decompress(taskInputMsg.getInputMessageContent()));

                LOGGER.debug("inputContent = {}", inputContent);
            } else {
                inputContent = "Message in binary format";
            }
        }

        TaskOutputMsg taskOutputMsg = service.getTaskOutputMsg(id);
        if (taskOutputMsg != null) {
            LOGGER.debug("taskOutputMsg = {}", taskOutputMsg);
            if (QifMessageType.TEXT.getName().equalsIgnoreCase(taskOutputMsg.getMsgType())) {
                outputContent = StringEscapeUtils.escapeXml11(StringCompressor.decompress(taskOutputMsg.getOutputMessageContent()));
                LOGGER.debug("outputContent = {}", outputContent);
            } else {
                outputContent = "Message in binary format";
            }
        }

        String allContent = inputContentTitle + inputContent + "<br />" + outputContentTitle + outputContent;
        return webPage.okResponse(allContent);
    }

    @GET
    @Path("/viewActivityData")
    @Produces(MediaType.TEXT_HTML)
    public Response viewActivityData(@QueryParam("id") String id) {
        StringBuilder content = new StringBuilder("<table class='table table-condensed table-bordered table-striped'>");
        content.append("<thead><tr>");
        content.append("<th>Data Key</th>");
        content.append("<th>Data Value</th>");
        content.append("</tr></thead>");
        content.append("<tbody>");
        List<ProcessInstanceData> dataList = service.selectActivityData(id);
        for (ProcessInstanceData data : dataList) {
            content.append("<tr>");
            content.append("<td>");
            content.append(data.getDataKey());
            content.append("</td>");
            content.append("<td>");
            content.append(Strings.nullToEmpty(data.getDataValue()));
            content.append("</td>");
            content.append("</tr>");
        }
        content.append("</tbody>");
        content.append("</table>");
        return webPage.okResponse(content.toString());
    }
}
