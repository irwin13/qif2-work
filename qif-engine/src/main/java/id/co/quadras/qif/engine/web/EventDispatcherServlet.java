package id.co.quadras.qif.engine.web;

import com.google.common.base.Strings;
import com.irwin13.winwork.basic.utilities.StringUtil;
import id.co.quadras.qif.core.HttpHeader;
import id.co.quadras.qif.core.QifActivity;
import id.co.quadras.qif.core.QifConstants;
import id.co.quadras.qif.core.QifProcess;
import id.co.quadras.qif.core.helper.JsonParser;
import id.co.quadras.qif.core.model.entity.QifEvent;
import id.co.quadras.qif.core.model.vo.QifActivityResult;
import id.co.quadras.qif.engine.guice.GuiceFactory;
import id.co.quadras.qif.engine.service.EventService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author irwin Timestamp : 06/06/2014 19:01
 */
public class EventDispatcherServlet extends HttpServlet {

    private static final String TEXT_PLAIN = "text/plain;charset=UTF-8";
    private static final Logger LOGGER = LoggerFactory.getLogger(EventDispatcherServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleRequest(request, response);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = request.getRequestURI().substring(request.getContextPath().length()).replaceFirst("/", "");
        LOGGER.debug("incoming request for path {}", path);

        String qifEventId = HttpEventMap.getMap().get(path);
        if (Strings.isNullOrEmpty(qifEventId)) {
            buildResponse(response, HttpServletResponse.SC_NOT_FOUND, null,
                    "404 : Not Found. No QifEvent configured with HTTP path '" + path + "'.", null);
        } else {
            EventService eventService = GuiceFactory.getInjector().getInstance(EventService.class);
            JsonParser jsonParser = GuiceFactory.getInjector().getInstance(JsonParser.class);

            QifEvent qifEvent = eventService.selectById(qifEventId);
            if (qifEvent == null) {
                buildResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null,
                        "500 : Internal Server Error. No QifEvent found with id '" + path + "'.", null);
            } else {
                if (qifEvent.getActiveAcceptMessage()) {

                    if (QifConstants.GET.equalsIgnoreCase(request.getMethod())) {
                        Map<String, String> message = new HashMap<String, String>();

                        Enumeration<String> headerNames = request.getHeaderNames();
                        if (headerNames != null) {
                            while (headerNames.hasMoreElements()) {
                                String header = headerNames.nextElement();
                                message.put(header, enumerationToString(request.getHeaders(header)));
                            }
                        }

                        Map<String, String[]> parameterMap = request.getParameterMap();
                        if (parameterMap != null) {
                            for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                                message.put(entry.getKey(), StringUtil.getFirst(entry.getValue()));
                            }
                        }

                        message.put(HttpHeader.REMOTE_ADDRESS, request.getRemoteAddr());

                        String jsonMessage = jsonParser.parseToString(false, message);
                        QifActivityResult result = executeEvent(jsonMessage, qifEvent);
                        buildResponseFromResult(response, result);
                    } else if (QifConstants.POST.equalsIgnoreCase(request.getMethod())) {
                        Map<String, String> message = new HashMap<String, String>();

                        Enumeration<String> headerNames = request.getHeaderNames();
                        while (headerNames.hasMoreElements()) {
                            String header = headerNames.nextElement();
                            message.put(header, enumerationToString(request.getHeaders(header)));
                        }

                        String httpBody = IOUtils.toString(request.getReader());
                        message.put(QifConstants.HTTP_BODY, httpBody);
                        message.put(HttpHeader.REMOTE_ADDRESS, request.getRemoteAddr());

                        String jsonMessage = jsonParser.parseToString(false, message);
                        QifActivityResult result = executeEvent(jsonMessage, qifEvent);
                        buildResponseFromResult(response, result);
                    } else {
                        String errorMessage = "405 : Method Not Allowed. HTTP Method " + request.getMethod() + " not supported, currently only support for GET and POST";
                        buildResponse(response, HttpServletResponse.SC_METHOD_NOT_ALLOWED, null, errorMessage, null);
                    }

                } else {
                    String errorMessage = "503 : Service Unavailable. QifEvent is not activated, please try again later";
                    buildResponse(response, HttpServletResponse.SC_SERVICE_UNAVAILABLE, null, errorMessage, null);
                }
            }
        }
    }

    private QifActivityResult executeEvent(String jsonMessage, QifEvent qifEvent) {
        QifActivityResult result;
        try {
            QifProcess qifProcess = (QifProcess) GuiceFactory.getInjector().getInstance(Class.forName(qifEvent.getQifProcess()));
            result = qifProcess.executeProcess(qifEvent, jsonMessage, null);
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
            String error = "FATAL : Class not found " + qifEvent.getQifProcess();
            result = new QifActivityResult(QifActivity.ERROR, error, null);
        }
        return result;
    }

    private void buildResponse(HttpServletResponse response, int statusCode, String contentType, String message,
                               Map<String, String> resultHeader)
            throws IOException {

        if (Strings.isNullOrEmpty(contentType)) {
            response.setContentType(TEXT_PLAIN);
        } else {
            response.setContentType(contentType);
        }

        response.setStatus(statusCode);
        if (message != null) {
            response.setContentLength(message.length());
        }

        if (resultHeader != null) {
            for (Map.Entry<String, String> entry : resultHeader.entrySet()) {
                LOGGER.debug("set header for HTTP Respose key = {} | value = {}", entry.getKey(), entry.getValue());
                response.setHeader(entry.getKey(), entry.getValue());
            }
        }
        response.getWriter().println(message);
    }

    private void buildResponseFromResult(HttpServletResponse response, QifActivityResult result)
            throws IOException {

        if (QifActivity.SUCCESS.equals(result.getStatus())) {
            String body = result.getResult().toString();
            buildResponse(response, HttpServletResponse.SC_OK, null, body, result.getAdditionalData());
        } else if (QifActivity.ERROR.equals(result.getStatus())) {
            String body = "500 Internal Server Error. " + result.getResult().toString();
            buildResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null, body, result.getAdditionalData());
        } else {
            String body = "500 Internal Server Error. Response status should be SUCCESS or ERROR";
            buildResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null, body, result.getAdditionalData());
        }
    }

    private String enumerationToString(Enumeration<String> enumeration) {
        StringBuilder value = new StringBuilder("");
        if (enumeration != null) {
            while (enumeration.hasMoreElements()) {
                String header = enumeration.nextElement();
                value.append(header + ",");
            }
        }
        return value.toString();
    }

}
