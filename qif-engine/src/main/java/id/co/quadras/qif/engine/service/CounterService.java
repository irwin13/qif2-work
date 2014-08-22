package id.co.quadras.qif.engine.service;

import id.co.quadras.qif.model.entity.QifCounter;
import id.co.quadras.qif.model.entity.QifEvent;

import java.util.List;

/**
 * @author irwin Timestamp : 04/06/2014 19:04
 */
public interface CounterService {
    public void incrementCounter(List<QifCounter> qifCounterList);
    public void batchInsert(List<QifCounter> qifCounterList);
    public void batchUpdate(List<QifCounter> qifCounterList);
    public List<QifCounter> select(QifCounter filter);
    public QifCounter selectByKey(String key);
    public void initCounter(List<QifEvent> eventList);
    public void insert(QifCounter model);
}
