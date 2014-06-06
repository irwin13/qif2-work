package id.co.quadras.qif.engine.service;

import id.co.quadras.qif.core.model.entity.QifEvent;

import java.util.List;

/**
 * @author irwin Timestamp : 12/05/2014 18:33
 */
public interface EventService {
    public QifEvent selectById(String id);
    public List<QifEvent> select(QifEvent filter);
}