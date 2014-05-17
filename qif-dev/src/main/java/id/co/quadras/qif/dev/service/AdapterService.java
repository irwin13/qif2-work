package id.co.quadras.qif.dev.service;

import id.co.quadras.qif.model.entity.QifAdapter;

import java.util.List;

/**
 * @author irwin Timestamp : 12/05/2014 18:34
 */
public interface AdapterService {
    public QifAdapter selectByName(String name);
    public QifAdapter selectById(String id);
}
