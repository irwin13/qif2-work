package id.co.quadras.qif.dev.service.imp;

import com.google.inject.Inject;
import id.co.quadras.qif.core.model.entity.QifCounter;
import id.co.quadras.qif.dev.dao.CounterDao;
import id.co.quadras.qif.dev.service.CounterService;

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
}
