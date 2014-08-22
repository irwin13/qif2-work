package id.co.quadras.qif.engine.counter;

import id.co.quadras.qif.engine.GuiceTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author irwin Timestamp : 05/05/2014 15:45
 */
public class QifTransactionCounterTest {

    final QifTransactionCounter qifTransactionCounter = GuiceTest.getInjector().getInstance(QifTransactionCounter.class);

    final String even = "even";
    final String odd = "odd";
    int loopCount = 10;

    @Test
    public void testCounter() throws InterruptedException {
        for (int i = 1; i <= loopCount; i++) {
            Thread thread;
            if (i % 2 == 0) {
                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        qifTransactionCounter.add(even);
                    }
                });
            } else {
                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        qifTransactionCounter.add(odd);
                    }
                });
            }
            thread.start();
        }

        // give 1 second for thread to complete it's mission
        Thread.sleep(1000);

        Assert.assertEquals(loopCount / 2, qifTransactionCounter.get(even));
        Assert.assertEquals(loopCount / 2, qifTransactionCounter.get(odd));

        QifTransactionCounter qifTransactionCounter = GuiceTest.getInjector().getInstance(QifTransactionCounter.class);
        Assert.assertEquals(loopCount / 2, qifTransactionCounter.get(even));

        qifTransactionCounter.reset(even, 0);
        Assert.assertEquals(0, qifTransactionCounter.get(even));

    }

}
