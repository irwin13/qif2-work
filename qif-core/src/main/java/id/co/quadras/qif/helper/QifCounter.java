package id.co.quadras.qif.helper;

/**
 * @author irwin Timestamp : 29/04/2014 16:15
 */
public interface QifCounter {
    public boolean add(String key);
    public int add(String key, int increment);
    public int get(String key);
    public int reset(String key, int newCounter);
    public boolean remove(String key);
}
