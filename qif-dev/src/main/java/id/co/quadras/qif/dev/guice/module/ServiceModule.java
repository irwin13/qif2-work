package id.co.quadras.qif.dev.guice.module;

import com.google.inject.AbstractModule;
import id.co.quadras.qif.dev.job.timertask.*;
import id.co.quadras.qif.dev.service.AdapterService;
import id.co.quadras.qif.dev.service.EventService;
import id.co.quadras.qif.dev.service.imp.AdapterServiceImp;
import id.co.quadras.qif.dev.service.imp.EventServiceImp;
import id.co.quadras.qif.dev.service.log.*;
import id.co.quadras.qif.dev.service.log.imp.*;

/**
 * @author irwin Timestamp : 12/05/2014 17:16
 */
public class ServiceModule extends AbstractModule {

    @Override
    protected void configure() {

        // LOG
        bind(ActivityLogService.class).to(ActivityLogServiceImp.class);
        bind(ActivityLogDataService.class).to(ActivityLogDataServiceImp.class);
        bind(ActivityLogInputMessageService.class).to(ActivityLogInputMessageServiceImp.class);
        bind(ActivityLogOutputMessageService.class).to(ActivityLogOutputMessageServiceImp.class);

        bind(EventLogService.class).to(EventLogServiceImp.class);
        bind(EventLogMessageService.class).to(EventLogMessageServiceImp.class);

        // CONFIGURATION
        bind(EventService.class).to(EventServiceImp.class);
        bind(AdapterService.class).to(AdapterServiceImp.class);

        // PERSIST
        bind(ActivityLogPersist.class);
        bind(ActivityLogDataPersist.class);
        bind(ActivityLogInputMessagePersist.class);
        bind(ActivityLogOutputMessagePersist.class);

        bind(EventLogPersist.class);
        bind(EventLogMessagePersist.class);
    }
}
