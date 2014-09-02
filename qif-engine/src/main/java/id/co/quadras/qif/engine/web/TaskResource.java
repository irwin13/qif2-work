package id.co.quadras.qif.engine.web;

import com.google.common.collect.ImmutableSortedSet;
import id.co.quadras.qif.engine.core.QifTask;
import id.co.quadras.qif.engine.guice.QifGuice;
import id.co.quadras.qif.engine.json.QifJsonParser;
import id.co.quadras.qif.engine.task.TaskRegister;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author irwin Timestamp : 02/09/2014 15:57
 */
@Path("/task-api")
public class TaskResource {

    private final QifJsonParser qifJsonParser = QifGuice.getInjector().getInstance(QifJsonParser.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get() throws IOException {
        Set<Class<? extends QifTask>> taskSet = TaskRegister.getTaskSet();
        Set<String> stringSet = new HashSet<String>();
        for (Class<?> clazz : taskSet) {
            stringSet.add(clazz.getName());
        }

        ImmutableSortedSet sortedSet = ImmutableSortedSet.copyOf(stringSet);
        String json = qifJsonParser.parseToString(false, sortedSet);
        return Response.ok(json).build();
    }
}
