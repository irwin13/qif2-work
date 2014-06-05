package id.co.quadras.qif.dev.job.timertask;

import com.google.inject.Inject;
import com.irwin13.winwork.basic.WinWorkConstants;
import id.co.quadras.qif.core.QifProcess;
import id.co.quadras.qif.core.QifTask;
import id.co.quadras.qif.core.helper.QifTransactionCounter;
import id.co.quadras.qif.core.helper.imp.QifTransactionCounterGuava;
import id.co.quadras.qif.core.model.entity.QifCounter;
import id.co.quadras.qif.core.model.entity.QifEvent;
import id.co.quadras.qif.dev.ProcessRegister;
import id.co.quadras.qif.dev.TaskRegister;
import id.co.quadras.qif.dev.service.CounterService;
import id.co.quadras.qif.dev.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author irwin Timestamp : 05/06/2014 16:44
 */
public class CounterUpdate extends TimerTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(CounterUpdate.class);

    private final QifTransactionCounter transactionCounter;
    private final CounterService counterService;
    private final EventService eventService;

    @Inject
    public CounterUpdate(QifTransactionCounter transactionCounter, CounterService counterService,
                         EventService eventService) {
        this.transactionCounter = transactionCounter;
        this.counterService = counterService;
        this.eventService = eventService;
    }

    @Override
    public void run() {
        List<QifCounter> qifCounterList = new LinkedList<QifCounter>();
        Map<String, Integer> mapCounter = new WeakHashMap<String, Integer>();

        populateData(qifCounterList, mapCounter);

        int index = 1;
        List<QifCounter> batchList = new LinkedList<QifCounter>();

        for (QifCounter qifCounter : qifCounterList) {
            batchList.add(qifCounter);
            if (index % 10 == 0 || index == qifCounterList.size()) {
                counterService.batchUpdate(batchList);
                LOGGER.debug("update counter on index {} with data count {}", index, batchList.size());

                for (QifCounter counter : batchList) {
                    int count = mapCounter.get(counter.getSequenceKey());
                    QifTransactionCounterGuava counterGuava = (QifTransactionCounterGuava) transactionCounter;
                    counterGuava.subtract(counter.getSequenceKey(), count);
                    LOGGER.debug("subtract key {} with count {}", counter.getSequenceKey(), count);
                }

                batchList.clear();
            }
            index++;
        }
    }

    private void populateData(List<QifCounter> qifCounterList, Map<String, Integer> mapCounter) {
        Date today = new Date();

        QifEvent filterEvent = new QifEvent();
        filterEvent.setActive(Boolean.TRUE);
        List<QifEvent> eventList = eventService.select(filterEvent);
        for (QifEvent qifEvent : eventList) {
            int countTotal = transactionCounter.get(qifEvent.getName());

            QifCounter qifCounterTotal = new QifCounter();
            qifCounterTotal.setSequenceKey(qifEvent.getName());
            qifCounterTotal.setLastSequence(countTotal);
            qifCounterTotal.setLastUpdateBy(getClass().getName());
            qifCounterTotal.setLastUpdateDate(today);
            qifCounterList.add(qifCounterTotal);

            mapCounter.put(qifEvent.getName(), countTotal);

            String keyPerDay = qifEvent.getName() + "_" + WinWorkConstants.SDF_DEFAULT.format(today);
            int countPerDay = transactionCounter.get(keyPerDay);

            QifCounter qifCounterPerDay = new QifCounter();
            qifCounterPerDay.setSequenceKey(keyPerDay);
            qifCounterPerDay.setLastSequence(countPerDay);
            qifCounterPerDay.setLastUpdateBy(getClass().getName());
            qifCounterPerDay.setLastUpdateDate(today);
            qifCounterList.add(qifCounterPerDay);

            mapCounter.put(keyPerDay, countPerDay);
        }

        Set<Class<? extends QifProcess>> processSet = ProcessRegister.getProcessSet();
        for (Class<? extends QifProcess> processClass : processSet) {
            int countTotal = transactionCounter.get(processClass.getName());

            QifCounter qifCounterTotal = new QifCounter();
            qifCounterTotal.setSequenceKey(processClass.getName());
            qifCounterTotal.setLastSequence(countTotal);
            qifCounterTotal.setLastUpdateBy(getClass().getName());
            qifCounterTotal.setLastUpdateDate(today);
            qifCounterList.add(qifCounterTotal);

            mapCounter.put(processClass.getName(), countTotal);

            String keyPerDay = processClass.getName() + "_" + WinWorkConstants.SDF_DEFAULT.format(today);
            int countPerDay = transactionCounter.get(keyPerDay);

            QifCounter qifCounterPerDay = new QifCounter();
            qifCounterPerDay.setSequenceKey(keyPerDay);
            qifCounterPerDay.setLastSequence(countPerDay);
            qifCounterPerDay.setLastUpdateBy(getClass().getName());
            qifCounterPerDay.setLastUpdateDate(today);
            qifCounterList.add(qifCounterPerDay);

            mapCounter.put(keyPerDay, countPerDay);
        }

        Set<Class<? extends QifTask>> taskSet = TaskRegister.getTaskSet();
        for (Class<? extends QifTask> taskClass : taskSet) {
            int countTotal = transactionCounter.get(taskClass.getName());

            QifCounter qifCounterTotal = new QifCounter();
            qifCounterTotal.setSequenceKey(taskClass.getName());
            qifCounterTotal.setLastSequence(countTotal);
            qifCounterTotal.setLastUpdateBy(getClass().getName());
            qifCounterTotal.setLastUpdateDate(today);
            qifCounterList.add(qifCounterTotal);

            mapCounter.put(taskClass.getName(), countTotal);

            String keyPerDay = taskClass.getName() + "_" + WinWorkConstants.SDF_DEFAULT.format(today);
            int countPerDay = transactionCounter.get(keyPerDay);

            QifCounter qifCounterPerDay = new QifCounter();
            qifCounterPerDay.setSequenceKey(keyPerDay);
            qifCounterPerDay.setLastSequence(countPerDay);
            qifCounterPerDay.setLastUpdateBy(getClass().getName());
            qifCounterPerDay.setLastUpdateDate(today);
            qifCounterList.add(qifCounterPerDay);

            mapCounter.put(keyPerDay, countPerDay);
        }
    }
}