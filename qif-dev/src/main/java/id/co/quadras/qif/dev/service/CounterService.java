package id.co.quadras.qif.dev.service;

import id.co.quadras.qif.model.entity.QifCounter;

import java.util.List;

/**
 * @author irwin Timestamp : 04/06/2014 19:04
 */
public interface CounterService {
    public void incrementCounter(String sequenceKey, String updateBy);
    public void batchInsert(List<QifCounter> qifCounterList);
}
