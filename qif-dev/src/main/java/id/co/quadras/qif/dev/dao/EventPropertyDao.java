package id.co.quadras.qif.dev.dao;

import id.co.quadras.qif.model.entity.QifEventProperty;

import java.util.List;

/**
 * @author irwin Timestamp : 15/05/2014 22:18
 */
public interface EventPropertyDao {
    public List<QifEventProperty> selectByEvent(String eventId);
}
