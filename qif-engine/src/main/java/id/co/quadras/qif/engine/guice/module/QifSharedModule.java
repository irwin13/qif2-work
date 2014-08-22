package id.co.quadras.qif.engine.guice.module;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.matcher.Matchers;
import com.irwin13.winwork.basic.annotations.MDCLog;
import com.irwin13.winwork.basic.log.MDCLogInterceptor;
import com.irwin13.winwork.basic.scheduler.BasicSchedulerManager;
import com.irwin13.winwork.basic.scheduler.SchedulerProvider;
import com.irwin13.winwork.basic.utilities.RestClient;
import id.co.quadras.qif.engine.SchedulerStarter;
import id.co.quadras.qif.engine.config.QifConfig;
import id.co.quadras.qif.engine.counter.QifTransactionCounter;
import id.co.quadras.qif.engine.counter.imp.QifTransactionCounterGuava;
import id.co.quadras.qif.engine.guice.provider.ExecutorServiceProvider;
import id.co.quadras.qif.engine.guice.provider.JsonMapperProvider;
import id.co.quadras.qif.engine.guice.provider.QifSqlSessionFactoryProvider;
import id.co.quadras.qif.engine.guice.provider.TomcatDataSourceProvider;
import id.co.quadras.qif.engine.json.JsonPrettyPrint;
import id.co.quadras.qif.engine.json.QifJsonParser;
import id.co.quadras.qif.engine.queue.*;
import id.co.quadras.qif.engine.queue.imp.*;
import id.co.quadras.qif.engine.queue.reader.*;
import id.co.quadras.qif.engine.queue.reader.imp.*;
import org.apache.ibatis.session.SqlSessionFactory;
import org.quartz.Scheduler;

import javax.sql.DataSource;
import java.util.concurrent.ExecutorService;

/**
 * @author irwin Timestamp : 12/05/2014 17:15
 */
public class QifSharedModule extends AbstractModule {

    private final QifConfig qifConfig;

    public QifSharedModule(QifConfig qifConfig) {
        this.qifConfig = qifConfig;
    }

    @Override
    protected void configure() {

        bind(QifConfig.class);
        bind(RestClient.class);

        // json
        bind(ObjectMapper.class).toProvider(JsonMapperProvider.class);
        bind(QifJsonParser.class);
        bind(JsonPrettyPrint.class).in(Singleton.class);

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

        // MyBatis
        bind(DataSource.class).toProvider(TomcatDataSourceProvider.class).in(Singleton.class);
        bind(SqlSessionFactory.class).toProvider(QifSqlSessionFactoryProvider.class).in(Singleton.class);

        bind(ExecutorService.class).toProvider(ExecutorServiceProvider.class).in(Singleton.class);
    }

}
