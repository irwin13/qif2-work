package id.co.quadras.qif.engine.service;

import id.co.quadras.qif.core.model.entity.QifAdapter;

/**
 * @author irwin Timestamp : 12/05/2014 18:34
 */
public interface AdapterService {
    public QifAdapter selectByName(String name);
    public QifAdapter selectById(String id);
}
