package id.co.quadras.qif.dev.service;

import id.co.quadras.qif.model.entity.QifAdapter;

import java.util.List;

/**
 * @author irwin Timestamp : 12/05/2014 18:34
 */
public interface AdapterInterface {
    public List<QifAdapter> select(QifAdapter filter);
    public QifAdapter selectById(String id);
}
