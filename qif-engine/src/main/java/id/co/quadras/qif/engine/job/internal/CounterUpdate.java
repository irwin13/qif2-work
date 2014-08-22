package id.co.quadras.qif.engine.job.internal;

import com.irwin13.winwork.basic.WinWorkConstants;
import id.co.quadras.qif.engine.QifEngineApplication;
import id.co.quadras.qif.engine.core.QifProcess;
import id.co.quadras.qif.engine.core.QifTask;
import id.co.quadras.qif.engine.counter.QifTransactionCounter;
import id.co.quadras.qif.engine.counter.imp.QifTransactionCounterGuava;
import id.co.quadras.qif.engine.process.ProcessRegister;
import id.co.quadras.qif.engine.service.CounterService;
import id.co.quadras.qif.engine.service.EventService;
import id.co.quadras.qif.engine.task.TaskRegister;
import id.co.quadras.qif.model.entity.QifCounter;
import id.co.quadras.qif.model.entity.QifEvent;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author irwin Timestamp : 05/06/2014 16:44
 */
@DisallowConcurrentExecution
public class CounterUpdate implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(CounterUpdate.class);
    private QifTransactionCounter transactionCounter;
    private EventService eventService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        transactionCounter = QifEngineApplication.getInjector().getInstance(QifTransactionCounter.class);
        CounterService counterService = QifEngineApplication.getInjector().getInstance(CounterService.class);
        eventService = QifEngineApplication.getInjector().getInstance(EventService.class);

        List<QifCounter> qifCounterList = new LinkedList<QifCounter>();
        Map<String, Integer> mapCounter = new WeakHashMap<String, Integer>();

        populateData(qifCounterList, mapCounter);

        counterService.incrementCounter(qifCounterList);

        for (QifCounter counter : qifCounterList) {
            int count = mapCounter.get(counter.getSequenceKey());
            QifTransactionCounterGuava counterGuava = (QifTransactionCounterGuava) transactionCounter;
            counterGuava.subtract(counter.getSequenceKey(), count);
            LOGGER.trace("subtract key {} with count {}", counter.getSequenceKey(), count);
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