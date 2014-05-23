package id.co.quadras.qif;

import com.google.inject.AbstractModule;
import id.co.quadras.qif.helper.QifCounter;
import id.co.quadras.qif.helper.imp.QifCounterGuava;
import id.co.quadras.qif.helper.queue.*;
import id.co.quadras.qif.helper.queue.imp.*;
import id.co.quadras.qif.helper.queue.reader.*;
import id.co.quadras.qif.helper.queue.reader.imp.*;

/**
 * @author irwin Timestamp : 05/05/2014 15:57
 */
public class HelperModule extends AbstractModule {

    @Override
    protected void configure() {
        // counter
        bind(QifCounter.class).to(QifCounterGuava.class);

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
