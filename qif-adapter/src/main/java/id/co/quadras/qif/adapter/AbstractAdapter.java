package id.co.quadras.qif.adapter;

import id.co.quadras.qif.model.entity.QifAdapter;

/**
 * @author irwin Timestamp : 21/05/2014 18:38
 */
public abstract class AbstractAdapter {

    protected final QifAdapter qifAdapter;

    public AbstractAdapter(QifAdapter qifAdapter) {
        this.qifAdapter = qifAdapter;
    }
}
