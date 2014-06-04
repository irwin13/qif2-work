package id.co.quadras.qif.dev.service.imp;

import com.google.inject.Inject;
import id.co.quadras.qif.dev.dao.CounterDao;
import id.co.quadras.qif.dev.service.CounterService;
import id.co.quadras.qif.model.entity.QifCounter;

import java.util.Date;
import java.util.List;

/**
 * @author irwin Timestamp : 04/06/2014 19:05
 */
public class CounterServiceImp implements CounterService {

    private final CounterDao counterDao;

    @Inject
    public CounterServiceImp(CounterDao counterDao) {
        this.counterDao = counterDao;
    }

    @Override
    public void incrementCounter(String sequenceKey, String updateBy) {
        QifCounter qifCounter = new QifCounter();
        qifCounter.setSequenceKey(sequenceKey);
        qifCounter.setLastUpdateBy(updateBy);
        qifCounter.setLastUpdateDate(new Date());
        counterDao.incrementCounter(qifCounter);
    }

    @Override
    public void batchInsert(List<QifCounter> qifCounterList) {
        counterDao.batchInsert(qifCounterList);
    }
}
