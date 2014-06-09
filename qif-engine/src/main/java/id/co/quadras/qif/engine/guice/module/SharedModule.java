package id.co.quadras.qif.engine.guice.module;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.matcher.Matchers;
import com.google.inject.name.Names;
import com.irwin13.winwork.basic.annotations.MDCLog;
import com.irwin13.winwork.basic.config.WinWorkConfig;
import com.irwin13.winwork.basic.log.MDCLogInterceptor;
import com.irwin13.winwork.basic.scheduler.BasicSchedulerManager;
import com.irwin13.winwork.basic.scheduler.SchedulerProvider;
import com.irwin13.winwork.basic.utilities.WinWorkVelocityUtil;
import id.co.quadras.qif.core.helper.JsonParser;
import id.co.quadras.qif.core.helper.QifTransactionCounter;
import id.co.quadras.qif.core.helper.imp.QifTransactionCounterGuava;
import id.co.quadras.qif.core.helper.queue.*;
import id.co.quadras.qif.core.helper.queue.imp.*;
import id.co.quadras.qif.core.helper.queue.reader.*;
import id.co.quadras.qif.core.helper.queue.reader.imp.*;
import id.co.quadras.qif.engine.QifConfig;
import id.co.quadras.qif.engine.SchedulerStarter;
import id.co.quadras.qif.engine.guice.provider.JsonMapperProvider;
import org.quartz.Scheduler;

/**
 * @author irwin Timestamp : 12/05/2014 17:15
 */
public class SharedModule extends AbstractModule {

    private final String qifConfigFile;

    public SharedModule(String qifConfigFile) {
        this.qifConfigFile = qifConfigFile;
    }

    @Override
    protected void configure() {

        // configuration file
        bind(String.class)
                .annotatedWith(Names.named("configFile"))
                .toInstance("common-config.xml");

        bind(String.class)
                .annotatedWith(Names.named("qifConfigFile"))
                .toInstance(qifConfigFile);

        bind(WinWorkConfig.class).to(QifConfig.class).in(Singleton.class);
        bind(WinWorkVelocityUtil.class);

        // json
        bind(ObjectMapper.class).toProvider(JsonMapperProvider.class);
        bind(JsonParser.class);

        // MDC Log AOP Interceptor
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(MDCLog.class), new MDCLogInterceptor());

        // scheduler components
        bind(Scheduler.class).toProvider(SchedulerProvider.class).in(Singleton.class);
        bind(BasicSchedulerManager.class);
        bind(SchedulerStarter.class);

        // counter
        bind(QifTransactionCounter.class).to(QifTransactionCounterGuava.class);

        // queue
        bind(EventLogQueue.class).to(EventLogQueueImp.class);
        bind(EventLogMsgQueue.class).to(EventLogMsgQueueImp.class);

        bind(ActivityLogQueue.class).to(ActivityLogQueueImp.class);
        bind(ActivityLogUpdateQueue.class).to(ActivityLogUpdateQueueImp.class);
        bind(ActivityLogDataQueue.class).to(ActivityLogDataQueueImp.class);
        bind(ActivityLogInputMsgQueue.class).to(ActivityLogInputMsgQueueImp.class);
        bind(ActivityLogOutputMsgQueue.class).to(ActivityLogOutputMsgQueueImp.class);

        // queue reader
        bind(EventLogQueueReader.class).to(EventLogQueueReaderImp.class);
        bind(EventLogMsgQueueReader.class).to(EventLogMsgQueueReaderImp.class);

        bind(ActivityLogQueueReader.class).to(ActivityLogQueueReaderImp.class);
        bind(ActivityLogUpdateQueueReader.class).to(ActivityLogUpdateQueueReaderImp.class);
        bind(ActivityLogDataQueueReader.class).to(ActivityLogDataQueueReaderImp.class);
        bind(ActivityLogInputMsgQueueReader.class).to(ActivityLogInputMsgQueueReaderImp.class);
        bind(ActivityLogOutputMsgQueueReader.class).to(ActivityLogOutputMsgQueueReaderImp.class);

    }
}
