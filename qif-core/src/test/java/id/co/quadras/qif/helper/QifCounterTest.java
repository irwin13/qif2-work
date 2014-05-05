package id.co.quadras.qif.helper;

import id.co.quadras.qif.GuiceQif;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author irwin Timestamp : 05/05/2014 15:45
 */
public class QifCounterTest {

    final QifCounter qifCounter = GuiceQif.getInjector().getInstance(QifCounter.class);

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
                        qifCounter.add(even);
                    }
                });
            } else {
                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        qifCounter.add(odd);
                    }
                });
            }
            thread.start();
        }

        // give 1 second for thread to complete it's mission
        Thread.sleep(1000);

        Assert.assertEquals(loopCount / 2, qifCounter.get(even));
        Assert.assertEquals(loopCount / 2, qifCounter.get(odd));
    }

    @Test
    public void testReset() {
        QifCounter qifCounter = GuiceQif.getInjector().getInstance(QifCounter.class);
        Assert.assertEquals(loopCount / 2, qifCounter.get(even));

        qifCounter.reset(even, 0);
        Assert.assertEquals(0, qifCounter.get(even));
    }

}
