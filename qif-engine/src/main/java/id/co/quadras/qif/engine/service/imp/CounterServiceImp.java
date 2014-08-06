package id.co.quadras.qif.engine.service.imp;

import com.google.inject.Inject;
import com.irwin13.winwork.basic.WinWorkConstants;
import com.irwin13.winwork.basic.utilities.StringUtil;
import id.co.quadras.qif.core.QifProcess;
import id.co.quadras.qif.core.QifTask;
import id.co.quadras.qif.core.model.entity.QifCounter;
import id.co.quadras.qif.core.model.entity.QifEvent;
import id.co.quadras.qif.engine.process.ProcessRegister;
import id.co.quadras.qif.engine.task.TaskRegister;
import id.co.quadras.qif.engine.dao.CounterDao;
import id.co.quadras.qif.engine.service.CounterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author irwin Timestamp : 04/06/2014 19:05
 */
public class CounterServiceImp implements CounterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CounterServiceImp.class);

    private final CounterDao counterDao;

    @Inject
    public CounterServiceImp(CounterDao counterDao) {
        this.counterDao = counterDao;
    }

    @Override
    public void incrementCounter(List<QifCounter> qifCounterList) {
        counterDao.incrementCounter(qifCounterList);
    }

    @Override
    public void batchInsert(List<QifCounter> qifCounterList) {
        counterDao.batchInsert(qifCounterList);
    }

    @Override
    public void batchUpdate(List<QifCounter> qifCounterList) {
        counterDao.batchUpdate(qifCounterList);
    }

    @Override
    public List<QifCounter> select(QifCounter filter) {
        return counterDao.select(filter);
    }

    @Override
    public QifCounter selectByKey(String key) {
        QifCounter qifCounter = null;
        QifCounter filter = new QifCounter();
        filter.setSequenceKey(key);
        filter.setActive(Boolean.TRUE);

        List<QifCounter> list = select(filter);
        if (!list.isEmpty()) {
            qifCounter = list.get(0);
        }
        return qifCounter;
    }

    @Override
    public void insert(QifCounter model) {
        counterDao.insert(model);
    }

    @Override
    public void initCounter(List<QifEvent> eventList) {
        List<QifCounter> insertList = new LinkedList<QifCounter>();
        populateData(insertList, eventList);
        batchInsert(insertList);
    }

    private void populateData(List<QifCounter> insertList, List<QifEvent> eventList) {
        Date today = new Date();

        for (QifEvent qifEvent : eventList) {

            QifCounter filterTotal = new QifCounter();
            filterTotal.setActive(Boolean.TRUE);
            filterTotal.setSequenceKey(qifEvent.getName());
            List<QifCounter> listTotal = select(filterTotal);
            if (listTotal.isEmpty()) {
                QifCounter qifCounterTotal = new QifCounter();

                qifCounterTotal.setSequenceKey(qifEvent.getName());
                qifCounterTotal.setLastSequence(0);

                qifCounterTotal.setId(StringUtil.random32UUID());
                qifCounterTotal.setActive(Boolean.TRUE);
                qifCounterTotal.setCreateBy(getClass().getName());
                qifCounterTotal.setCreateDate(today);
                qifCounterTotal.setLastUpdateBy(getClass().getName());
                qifCounterTotal.setLastUpdateDate(today);
                insertList.add(qifCounterTotal);
            }

            String keyPerDay = qifEvent.getName() + "_" + WinWorkConstants.SDF_DEFAULT.format(today);

            QifCounter filterPerDay = new QifCounter();
            filterPerDay.setActive(Boolean.TRUE);
            filterPerDay.setSequenceKey(keyPerDay);
            List<QifCounter> listPerDay = select(filterPerDay);
            if (listPerDay.isEmpty()) {
                QifCounter qifCounterPerDay = new QifCounter();

                qifCounterPerDay.setSequenceKey(keyPerDay);
                qifCounterPerDay.setLastSequence(0);

                qifCounterPerDay.setId(StringUtil.random32UUID());
                qifCounterPerDay.setActive(Boolean.TRUE);
                qifCounterPerDay.setCreateBy(getClass().getName());
                qifCounterPerDay.setCreateDate(today);
                qifCounterPerDay.setLastUpdateBy(getClass().getName());
                qifCounterPerDay.setLastUpdateDate(today);
                insertList.add(qifCounterPerDay);
            }
        }

        Set<Class<? extends QifProcess>> processSet = ProcessRegister.getProcessSet();
        for (Class<? extends QifProcess> processClass : processSet) {
            QifCounter filterTotal = new QifCounter();
            filterTotal.setActive(Boolean.TRUE);
            filterTotal.setSequenceKey(processClass.getName());
            List<QifCounter> listTotal = select(filterTotal);
            if (listTotal.isEmpty()) {
                QifCounter qifCounterTotal = new QifCounter();

                qifCounterTotal.setSequenceKey(processClass.getName());
                qifCounterTotal.setLastSequence(0);

                qifCounterTotal.setId(StringUtil.random32UUID());
                qifCounterTotal.setActive(Boolean.TRUE);
                qifCounterTotal.setCreateBy(getClass().getName());
                qifCounterTotal.setCreateDate(today);
                qifCounterTotal.setLastUpdateBy(getClass().getName());
                qifCounterTotal.setLastUpdateDate(today);
                insertList.add(qifCounterTotal);
            }

            String keyPerDay = processClass.getName() + "_" + WinWorkConstants.SDF_DEFAULT.format(today);

            QifCounter filterPerDay = new QifCounter();
            filterPerDay.setActive(Boolean.TRUE);
            filterPerDay.setSequenceKey(keyPerDay);
            List<QifCounter> listPerDay = select(filterPerDay);
            if (listPerDay.isEmpty()) {
                QifCounter qifCounterPerDay = new QifCounter();

                qifCounterPerDay.setSequenceKey(keyPerDay);
                qifCounterPerDay.setLastSequence(0);

                qifCounterPerDay.setId(StringUtil.random32UUID());
                qifCounterPerDay.setActive(Boolean.TRUE);
                qifCounterPerDay.setCreateBy(getClass().getName());
                qifCounterPerDay.setCreateDate(today);
                qifCounterPerDay.setLastUpdateBy(getClass().getName());
                qifCounterPerDay.setLastUpdateDate(today);
                insertList.add(qifCounterPerDay);
            }
        }

        Set<Class<? extends QifTask>> taskSet = TaskRegister.getTaskSet();
        for (Class<? extends QifTask> taskClass : taskSet) {
            QifCounter filterTotal = new QifCounter();
            filterTotal.setActive(Boolean.TRUE);
            filterTotal.setSequenceKey(taskClass.getName());
            List<QifCounter> listTotal = select(filterTotal);
            if (listTotal.isEmpty()) {
                QifCounter qifCounterTotal = new QifCounter();

                qifCounterTotal.setSequenceKey(taskClass.getName());
                qifCounterTotal.setLastSequence(0);

                qifCounterTotal.setId(StringUtil.random32UUID());
                qifCounterTotal.setActive(Boolean.TRUE);
                qifCounterTotal.setCreateBy(getClass().getName());
                qifCounterTotal.setCreateDate(today);
                qifCounterTotal.setLastUpdateBy(getClass().getName());
                qifCounterTotal.setLastUpdateDate(today);
                insertList.add(qifCounterTotal);
            }

            String keyPerDay = taskClass.getName() + "_" + WinWorkConstants.SDF_DEFAULT.format(today);

            QifCounter filterPerDay = new QifCounter();
            filterPerDay.setActive(Boolean.TRUE);
            filterPerDay.setSequenceKey(keyPerDay);
            List<QifCounter> listPerDay = select(filterPerDay);
            if (listPerDay.isEmpty()) {
                QifCounter qifCounterPerDay = new QifCounter();

                qifCounterPerDay.setSequenceKey(keyPerDay);
                qifCounterPerDay.setLastSequence(0);

                qifCounterPerDay.setId(StringUtil.random32UUID());
                qifCounterPerDay.setActive(Boolean.TRUE);
                qifCounterPerDay.setCreateBy(getClass().getName());
                qifCounterPerDay.setCreateDate(today);
                qifCounterPerDay.setLastUpdateBy(getClass().getName());
                qifCounterPerDay.setLastUpdateDate(today);
                insertList.add(qifCounterPerDay);
            }
        }
    }
}
