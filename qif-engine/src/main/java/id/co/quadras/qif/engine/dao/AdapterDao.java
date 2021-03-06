package id.co.quadras.qif.engine.dao;

import id.co.quadras.qif.model.entity.QifAdapter;

import java.util.List;

/**
 * @author irwin Timestamp : 15/05/2014 22:17
 */
public interface AdapterDao {
    public List<QifAdapter> select(QifAdapter filter);
    public QifAdapter selectById(String id);
    public void update(QifAdapter qifAdapter);
}
