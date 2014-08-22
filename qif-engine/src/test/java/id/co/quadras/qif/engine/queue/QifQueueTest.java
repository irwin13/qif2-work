package id.co.quadras.qif.engine.queue;

import id.co.quadras.qif.engine.GuiceTest;
import id.co.quadras.qif.engine.queue.reader.EventLogQueueReader;
import id.co.quadras.qif.model.entity.log.QifEventLog;
import org.junit.Assert;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.List;

/**
 * @author irwin Timestamp : 05/05/2014 17:08
 */
public class QifQueueTest {

    private final EventLogQueue queue = GuiceTest.getInjector().getInstance(EventLogQueue.class);
    private final EventLogQueueReader queueReader = GuiceTest.getInjector().getInstance(EventLogQueueReader.class);
    private final int messageCount = 10;
    private final PodamFactory podamFactory = new PodamFactoryImpl();

    @Test
    public void queueOperation() {
        for (int i = 0; i < messageCount; i++) {
            QifEventLog log = podamFactory.manufacturePojo(QifEventLog.class);
            queue.put(log);
        }

        Assert.assertEquals(messageCount, queue.size());

        List<QifEventLog> logList = queueReader.getLogList(messageCount);
        Assert.assertEquals(messageCount, logList.size());
    }
}
