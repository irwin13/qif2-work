package id.co.quadras.qif.engine.core;

import id.co.quadras.qif.engine.connector.adapter.AbstractAdapter;
import id.co.quadras.qif.model.entity.QifAdapter;

/**
 * @author irwin Timestamp : 28/08/2014 16:46
 */
public abstract class SingletonAdapter extends AbstractAdapter {

    protected SingletonAdapter(QifAdapter qifAdapter) throws Exception{
        super(qifAdapter);
        initSingletonObjects();
    }

    protected abstract void initSingletonObjects() throws Exception;
    protected abstract void cleanSingletonObjects() throws Exception;
}
