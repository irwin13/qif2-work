package id.co.quadras.qif.dev.dao;

import id.co.quadras.qif.core.model.entity.QifCounter;

import java.util.List;

/**
 * @author irwin Timestamp : 04/06/2014 18:57
 */
public interface CounterDao {
    public void incrementCounter(List<QifCounter> qifCounterList);
    public void batchInsert(List<QifCounter> qifCounterList);
    public void batchUpdate(List<QifCounter> qifCounterList);
    public List<QifCounter> select(QifCounter filter);
    public void insert(QifCounter model);
    public void update(QifCounter model);
}
