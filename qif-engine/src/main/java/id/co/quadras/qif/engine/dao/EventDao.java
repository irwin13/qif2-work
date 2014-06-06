package id.co.quadras.qif.engine.dao;

import id.co.quadras.qif.core.model.entity.QifEvent;

import java.util.List;

/**
 * @author irwin Timestamp : 15/05/2014 22:17
 */
public interface EventDao {
    public List<QifEvent> select(QifEvent filter);
    public QifEvent selectById(String id);
}