package id.co.quadras.qif.engine.web.servlet;

import com.google.common.base.Strings;
import com.google.common.net.MediaType;
import id.co.quadras.qif.engine.QifEngineApplication;
import id.co.quadras.qif.engine.core.QifActivity;
import id.co.quadras.qif.engine.core.QifProcess;
import id.co.quadras.qif.engine.guice.QifGuice;
import id.co.quadras.qif.engine.json.QifJsonParser;
import id.co.quadras.qif.engine.service.EventService;
import id.co.quadras.qif.model.entity.QifEvent;
import id.co.quadras.qif.model.vo.HttpRequestMessage;
import id.co.quadras.qif.model.vo.QifActivityResult;
import id.co.quadras.qif.model.vo.event.EventHttp;
import id.co.quadras.qif.model.vo.message.QifMessageType;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.net.util.Base64;
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

    public static final String EVENT_PATH = "/http-event/";
    public static final String TEXT_PLAIN = "text/plain;charset=UTF-8";
    private static final Logger LOGGER = LoggerFactory.getLogger(EventDispatcherServlet.class);

    private final QifJsonParser qifJsonParser = QifGuice.getInjector().getInstance(QifJsonParser.class);

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
        if (QifEngineApplication.isActive()) {
            String path = request.getRequestURI().substring(request.getContextPath().length()).replaceFirst(EVENT_PATH, "");
            LOGGER.debug("incoming request for path {}", path);

            EventService eventService = QifGuice.getInjector().getInstance(EventService.class);
            QifEvent qifEventPath = eventService.selectByProperty(EventHttp.HTTP_PATH.getName(), path);
            QifEvent qifEventMethod = eventService.selectByProperty(EventHttp.HTTP_METHOD.getName(), request.getMethod().toLowerCase());
            if (qifEventPath == null) {
                buildResponse(response, HttpServletResponse.SC_NOT_FOUND, TEXT_PLAIN,
                        "404 : Not Found. No QifEvent configured with HTTP path '" + path + "'", null);
            } else {
                if (qifEventPath.getActiveAcceptMessage()) {
                    if (qifEventMethod != null) {
                        QifActivityResult result = executeEvent(request, qifEventPath);
                        buildResponseFromResult(response, result);
                    } else {
                        String errorMessage = "405 : Method Not Allowed. HTTP Method not match, QifEvent '" + qifEventPath.getName()
                                + "' is not configured with HTTP Method '" + request.getMethod().toLowerCase() + "'";
                        buildResponse(response, HttpServletResponse.SC_METHOD_NOT_ALLOWED, TEXT_PLAIN, errorMessage, null);
                    }

                } else {
                    String errorMessage = "503 : Service Unavailable. QifEvent is not activated, please try again later";
                    buildResponse(response, HttpServletResponse.SC_SERVICE_UNAVAILABLE, TEXT_PLAIN, errorMessage, null);
                }
            }
        } else {
            String errorMessage = "503 : Service Unavailable. QIF is shutting down";
            buildResponse(response, HttpServletResponse.SC_SERVICE_UNAVAILABLE, TEXT_PLAIN, errorMessage, null);
        }
    }

    private QifActivityResult executeEvent(HttpServletRequest request, QifEvent qifEvent) {
        QifActivityResult result;
        try {
            QifProcess qifProcess = (QifProcess) QifGuice.getInjector().getInstance(Class.forName(qifEvent.getQifProcess()));
            result = qifProcess.executeEvent(qifEvent, copyHttpServletRequest(request), QifMessageType.OBJECT);
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
            String error = "FATAL : Class not found " + qifEvent.getQifProcess();
            result = new QifActivityResult(QifActivity.ERROR, error, QifMessageType.STRING);
        } catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage(), e);
            String error = ExceptionUtils.getStackTrace(e);
            result = new QifActivityResult(QifActivity.ERROR, error, QifMessageType.STRING);
        }

        return result;
    }

    private void buildResponse(HttpServletResponse response, int statusCode, String contentType, String message,
                               Map<String, Object> resultHeader)
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
            for (Map.Entry<String, Object> entry : resultHeader.entrySet()) {
                LOGGER.debug("set header for HTTP response with key = {} | value = {}", entry.getKey(), entry.getValue());
                Object value = entry.getValue();
                response.setHeader(entry.getKey(), (value != null) ? entry.getValue().toString() : null);
            }
        }
        response.getWriter().println(message);
    }

    private void buildResponseFromResult(HttpServletResponse response, QifActivityResult result)
            throws IOException {

        if (result != null) {
            if (QifActivity.SUCCESS.equals(result.getStatus())) {
                if (result.getResult() != null) {
                    if (QifMessageType.STRING.equals(result.getMessageType())) {
                        String body = (String) result.getResult();
                        buildResponse(response, HttpServletResponse.SC_OK, TEXT_PLAIN, body, result.getActivityData());
                    } else if (QifMessageType.OBJECT.equals(result.getMessageType())) {
                        String body = qifJsonParser.parseToString(false, result.getResult());
                        buildResponse(response, HttpServletResponse.SC_OK, MediaType.JSON_UTF_8.toString(), body, result.getActivityData());
                    } else if (QifMessageType.BINARY.equals(result.getMessageType())) {
                        String body = new String(Base64.encodeBase64((byte[]) result.getResult()));
                        buildResponse(response, HttpServletResponse.SC_OK, MediaType.APPLICATION_BINARY.toString(), body, result.getActivityData());
                    }
                } else {
                    buildResponse(response, HttpServletResponse.SC_OK, TEXT_PLAIN, QifActivity.SUCCESS, result.getActivityData());
                }
            } else if (QifActivity.ERROR.equals(result.getStatus())) {
                String body = "500 Internal Server Error. " + result.getResult();
                buildResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, TEXT_PLAIN, body, result.getActivityData());
            } else {
                String body = "500 Internal Server Error. Response status should be SUCCESS or ERROR";
                buildResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, TEXT_PLAIN, body, result.getActivityData());
            }
        } else {
            buildResponse(response, HttpServletResponse.SC_OK, TEXT_PLAIN, null, null);
        }
    }

    private HttpRequestMessage copyHttpServletRequest(HttpServletRequest httpRequest) {
        HttpRequestMessage requestMessage = new HttpRequestMessage();

        requestMessage.setCharacterEncoding(httpRequest.getCharacterEncoding());
        requestMessage.setContentLength(httpRequest.getContentLength());
        requestMessage.setContentType(httpRequest.getContentType());
        requestMessage.setContextPath(httpRequest.getContextPath());
        requestMessage.setLocalAddr(httpRequest.getLocalAddr());
        requestMessage.setLocalName(httpRequest.getLocalName());
        requestMessage.setLocalPort(httpRequest.getLocalPort());
        requestMessage.setMethod(httpRequest.getMethod());
        requestMessage.setPathInfo(httpRequest.getPathInfo());
        requestMessage.setPathTranslated(httpRequest.getPathTranslated());
        requestMessage.setProtocol(httpRequest.getProtocol());
        requestMessage.setQueryString(httpRequest.getQueryString());
        requestMessage.setRemoteAddr(httpRequest.getRemoteAddr());
        requestMessage.setRemoteHost(httpRequest.getRemoteHost());
        requestMessage.setRemotePort(httpRequest.getRemotePort());
        requestMessage.setRemoteUser(httpRequest.getRemoteUser());
        requestMessage.setRequestURI(httpRequest.getRequestURI());
        requestMessage.setScheme(httpRequest.getScheme());
        requestMessage.setServerName(httpRequest.getServerName());
        requestMessage.setServerPort(httpRequest.getServerPort());
        requestMessage.setServletPath(httpRequest.getServletPath());
        requestMessage.setRequestParameter(httpRequest.getParameterMap());

        try {
            requestMessage.setHttpBody(IOUtils.toString(httpRequest.getReader()));
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }

        Map<String, String> httpHeader = new HashMap<String, String>();
        Enumeration<String> headerNames = httpRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = httpRequest.getHeader(headerName);
            httpHeader.put(headerName, headerValue);
        }
        requestMessage.setHttpHeader(httpHeader);

        return requestMessage;
    }
}
