package id.co.quadras.qif.core.helper.queue;

/**
 * @author irwin Timestamp : 29/04/2014 16:18
 */
public interface QifQueue<T> {
    public boolean put(T t);
    public T get();
    public int size();
}
