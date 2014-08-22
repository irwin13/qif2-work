package id.co.quadras.qif.engine.module;

import com.google.inject.AbstractModule;
import id.co.quadras.qif.engine.counter.QifTransactionCounter;
import id.co.quadras.qif.engine.counter.imp.QifTransactionCounterGuava;
import id.co.quadras.qif.engine.queue.*;
import id.co.quadras.qif.engine.queue.imp.*;
import id.co.quadras.qif.engine.queue.reader.*;
import id.co.quadras.qif.engine.queue.reader.imp.*;

/**
 * @author irwin Timestamp : 22/08/2014 14:50
 */
public class TestModule extends AbstractModule {

    @Override
    protected void configure() {
        // counter
        bind(QifTransactionCounter.class).to(QifTransactionCounterGuava.class);

        // queue
        bind(ActivityLogQueue.class).to(ActivityLogQueueImp.class);
        bind(ActivityLogDataQueue.class).to(ActivityLogDataQueueImp.class);
        bind(ActivityLogInputMsgQueue.class).to(ActivityLogInputMsgQueueImp.class);
        bind(ActivityLogOutputMsgQueue.class).to(ActivityLogOutputMsgQueueImp.class);

        bind(EventLogQueue.class).to(EventLogQueueImp.class);
        bind(EventLogMsgQueue.class).to(EventLogMsgQueueImp.class);

        // queue reader
        bind(ActivityLogQueueReader.class).to(ActivityLogQueueReaderImp.class);
        bind(ActivityLogDataQueueReader.class).to(ActivityLogDataQueueReaderImp.class);
        bind(ActivityLogInputMsgQueueReader.class).to(ActivityLogInputMsgQueueReaderImp.class);
        bind(ActivityLogOutputMsgQueueReader.class).to(ActivityLogOutputMsgQueueReaderImp.class);

        bind(EventLogQueueReader.class).to(EventLogQueueReaderImp.class);
        bind(EventLogMsgQueueReader.class).to(EventLogMsgQueueReaderImp.class);

    }

}
