package id.co.quadras.qif.dev.job.timertask;

import com.google.inject.Inject;
import id.co.quadras.qif.core.helper.QifTransactionCounter;
import id.co.quadras.qif.core.model.entity.QifCounter;
import id.co.quadras.qif.dev.service.CounterService;

import java.util.*;

/**
 * @author irwin Timestamp : 05/06/2014 16:44
 */
public class CounterUpdate extends TimerTask {

    private final QifTransactionCounter transactionCounter;
    private final CounterService counterService;

    @Inject
    public CounterUpdate(QifTransactionCounter transactionCounter, CounterService counterService) {
        this.transactionCounter = transactionCounter;
        this.counterService = counterService;
    }

    @Override
    public void run() {
        Set<String> keySet = transactionCounter.getKeys();

        int index = 1;
        Date today = new Date();
        List<QifCounter> qifCounterList = new LinkedList<QifCounter>();
        List<Integer> counterList = new LinkedList<Integer>();

        for (String key : keySet) {
            QifCounter qifCounter = new QifCounter();

            qifCounter.setSequenceKey(key);
            qifCounter.setLastUpdateBy(getClass().getName());
            qifCounter.setLastUpdateDate(today);
            index++;
        }
    }
}
