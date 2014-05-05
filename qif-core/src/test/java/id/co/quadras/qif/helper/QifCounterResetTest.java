package id.co.quadras.qif.helper;

import id.co.quadras.qif.GuiceQif;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author irwin Timestamp : 05/05/2014 16:52
 */
public class QifCounterResetTest {

    @Test
    public void testReset() {
        final String key = "keyReset";
        QifCounter qifCounter = GuiceQif.getInjector().getInstance(QifCounter.class);
        qifCounter.add(key, 10);
        Assert.assertEquals(10, qifCounter.get(key));

        qifCounter.reset(key, 0);
        Assert.assertEquals(0, qifCounter.get(key));
    }
}
