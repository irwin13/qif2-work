package id.co.quadras.qif.engine.web.servlet;

import com.google.common.collect.ImmutableSortedSet;
import com.google.common.net.MediaType;
import id.co.quadras.qif.engine.core.QifTask;
import id.co.quadras.qif.engine.guice.QifGuice;
import id.co.quadras.qif.engine.json.QifJsonParser;
import id.co.quadras.qif.engine.task.TaskRegister;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author irwin Timestamp : 03/07/2014 16:42
 */
@Deprecated
public class TaskApiServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        Set<Class<? extends QifTask>> taskSet = TaskRegister.getTaskSet();
        Set<String> stringSet = new HashSet<String>();
        for (Class<?> clazz : taskSet) {
            stringSet.add(clazz.getName());
        }

        ImmutableSortedSet sortedSet = ImmutableSortedSet.copyOf(stringSet);
        QifJsonParser qifJsonParser = QifGuice.getInjector().getInstance(QifJsonParser.class);
        String json = qifJsonParser.parseToString(false, sortedSet);

        response.setContentLength(json.length());
        response.setContentType(MediaType.JSON_UTF_8.toString());
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print(json);
    }
}
