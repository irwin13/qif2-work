package id.co.quadras.qif.engine.web;

import com.irwin13.winwork.basic.WinWorkConstants;
import com.irwin13.winwork.basic.config.WinWorkConfig;
import com.irwin13.winwork.basic.utilities.WinWorkUtil;
import id.co.quadras.qif.engine.QifEngineApplication;
import id.co.quadras.qif.engine.guice.QifGuice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * @author irwin Timestamp : 25/08/2014 14:09
 */
@Path("/app-status")
public class AppStatusResource {

    private WinWorkConfig config = QifGuice.getInjector().getInstance(WinWorkConfig.class);

    @GET
    public Response appStatusPage() {
        String htmlContent = WinWorkUtil.appStatusView(config, QifEngineApplication.START, WinWorkConstants.SDF_FULL);
        return Response.ok(htmlContent).build();
    }
}
