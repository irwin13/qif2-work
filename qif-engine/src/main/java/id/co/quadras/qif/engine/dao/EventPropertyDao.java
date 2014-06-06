package id.co.quadras.qif.engine.dao;

import id.co.quadras.qif.core.model.entity.QifEventProperty;

import java.util.List;

/**
 * @author irwin Timestamp : 15/05/2014 22:18
 */
public interface EventPropertyDao {
    public List<QifEventProperty> selectByEvent(String eventId);
}
