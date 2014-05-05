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
        bind(ActivityLogInputMessageQueue.class).to(ActivityLogInputMessageQueueImp.class);
        bind(ActivityLogOutputMessageQueue.class).to(ActivityLogOutputMessageQueueImp.class);

        bind(EventLogQueue.class).to(EventLogQueueImp.class);
        bind(EventLogMessageQueue.class).to(EventLogMessageQueueImp.class);

        // queue reader
        bind(ActivityLogQueueReader.class).to(ActivityLogQueueReaderImp.class);
        bind(ActivityLogDataQueueReader.class).to(ActivityLogDataQueueReaderImp.class);
        bind(ActivityLogInputMessageQueueReader.class).to(ActivityLogInputMessageQueueReaderImp.class);
        bind(ActivityLogOutputMessageQueueReader.class).to(ActivityLogOutputMessageQueueReaderImp.class);

        bind(EventLogQueueReader.class).to(EventLogQueueReaderImp.class);
        bind(EventLogMessageQueueReader.class).to(EventLogMessageQueueReaderImp.class);

    }
}
