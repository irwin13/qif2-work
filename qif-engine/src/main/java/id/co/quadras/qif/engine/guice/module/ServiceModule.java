package id.co.quadras.qif.engine.guice.module;

import com.google.inject.AbstractModule;
import com.irwin13.winwork.basic.service.BasicEntityCommonService;
import id.co.quadras.qif.engine.job.internal.*;
import id.co.quadras.qif.engine.service.AdapterService;
import id.co.quadras.qif.engine.service.CounterService;
import id.co.quadras.qif.engine.service.EventService;
import id.co.quadras.qif.engine.service.app.AppSettingService;
import id.co.quadras.qif.engine.service.app.imp.AppSettingServiceImp;
import id.co.quadras.qif.engine.service.imp.AdapterServiceImp;
import id.co.quadras.qif.engine.service.imp.CounterServiceImp;
import id.co.quadras.qif.engine.service.imp.EventServiceImp;
import id.co.quadras.qif.engine.service.log.*;
import id.co.quadras.qif.engine.service.log.imp.*;

/**
 * @author irwin Timestamp : 12/05/2014 17:16
 */
public class ServiceModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(BasicEntityCommonService.class);

        // LOG
        bind(ActivityLogService.class).to(ActivityLogServiceImp.class);
        bind(ActivityLogDataService.class).to(ActivityLogDataServiceImp.class);
        bind(ActivityLogInputMsgService.class).to(ActivityLogInputMsgServiceImp.class);
        bind(ActivityLogOutputMsgService.class).to(ActivityLogOutputMsgServiceImp.class);

        bind(EventLogService.class).to(EventLogServiceImp.class);
        bind(EventLogMsgService.class).to(EventLogMsgServiceImp.class);

        // CONFIGURATION
        bind(EventService.class).to(EventServiceImp.class);
        bind(AdapterService.class).to(AdapterServiceImp.class);

        // PERSIST
        bind(ActivityLogPersist.class);
        bind(ActivityLogDataPersist.class);
        bind(ActivityLogInputMsgPersist.class);
        bind(ActivityLogOutputMsgPersist.class);

        bind(EventLogPersist.class);
        bind(EventLogMsgPersist.class);

        // COUNTER
        bind(CounterService.class).to(CounterServiceImp.class);
        bind(CounterUpdate.class);

        // APP
        bind(AppSettingService.class).to(AppSettingServiceImp.class);
    }
}
