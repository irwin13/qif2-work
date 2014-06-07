package id.co.quadras.qif.engine.web;

import com.google.common.base.Strings;
import id.co.quadras.qif.core.QifActivity;
import id.co.quadras.qif.core.QifConstants;
import id.co.quadras.qif.core.QifProcess;
import id.co.quadras.qif.core.model.entity.QifEvent;
import id.co.quadras.qif.core.model.vo.QifActivityResult;
import id.co.quadras.qif.core.model.vo.event.EventHttp;
import id.co.quadras.qif.engine.guice.GuiceFactory;
import id.co.quadras.qif.engine.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

        EventService eventService = GuiceFactory.getInjector().getInstance(EventService.class);
        QifEvent qifEvent = eventService.selectByProperty(EventHttp.HTTP_PATH.getName(), path);
        if (qifEvent == null) {
            buildResponse(response, HttpServletResponse.SC_NOT_FOUND, TEXT_PLAIN,
                    "404 : Not Found. No QifEvent configured with HTTP path '" + path + "'", null);
        } else {
            if (qifEvent.getActiveAcceptMessage()) {

                if (QifConstants.GET.equalsIgnoreCase(request.getMethod()) ||
                        QifConstants.POST.equalsIgnoreCase(request.getMethod())) {

                    QifActivityResult result = executeEvent(request, qifEvent);
                    buildResponseFromResult(response, result);
                } else {
                    String errorMessage = "405 : Method Not Allowed. HTTP Method " + request.getMethod() + " not supported, currently only support for GET and POST";
                    buildResponse(response, HttpServletResponse.SC_METHOD_NOT_ALLOWED, TEXT_PLAIN, errorMessage, null);
                }

            } else {
                String errorMessage = "503 : Service Unavailable. QifEvent is not activated, please try again later";
                buildResponse(response, HttpServletResponse.SC_SERVICE_UNAVAILABLE, TEXT_PLAIN, errorMessage, null);
            }
        }
    }

    private QifActivityResult executeEvent(HttpServletRequest request, QifEvent qifEvent) {
        QifActivityResult result;
        try {
            QifProcess qifProcess = (QifProcess) GuiceFactory.getInjector().getInstance(Class.forName(qifEvent.getQifProcess()));
            result = qifProcess.executeProcess(qifEvent, request, null);
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
                LOGGER.debug("set header for HTTP response with key = {} | value = {}", entry.getKey(), entry.getValue());
                response.setHeader(entry.getKey(), entry.getValue());
            }
        }
        response.getWriter().println(message);
    }

    private void buildResponseFromResult(HttpServletResponse response, QifActivityResult result)
            throws IOException {

        if (QifActivity.SUCCESS.equals(result.getStatus())) {
            String body = result.getResult().toString();
            buildResponse(response, HttpServletResponse.SC_OK, TEXT_PLAIN, body, result.getAdditionalData());
        } else if (QifActivity.ERROR.equals(result.getStatus())) {
            String body = "500 Internal Server Error. " + result.getResult().toString();
            buildResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, TEXT_PLAIN, body, result.getAdditionalData());
        } else {
            String body = "500 Internal Server Error. Response status should be SUCCESS or ERROR";
            buildResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, TEXT_PLAIN, body, result.getAdditionalData());
        }
    }

}
