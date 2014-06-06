package id.co.quadras.qif.engine.dao;

import id.co.quadras.qif.core.model.entity.QifAdapterProperty;

import java.util.List;

/**
 * @author irwin Timestamp : 15/05/2014 22:17
 */
public interface AdapterPropertyDao {
    public List<QifAdapterProperty> selectByAdapter(String adapterId);
}
