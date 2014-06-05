package id.co.quadras.qif.dev.dao;

import id.co.quadras.qif.core.model.entity.QifAdapter;

import java.util.List;

/**
 * @author irwin Timestamp : 15/05/2014 22:17
 */
public interface AdapterDao {
    public List<QifAdapter> select(QifAdapter filter);
    public QifAdapter selectById(String id);
}
