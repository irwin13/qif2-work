package id.co.quadras.qif.helper.imp;

import com.google.common.collect.ConcurrentHashMultiset;
import id.co.quadras.qif.helper.QifCounter;

/**
 * @author irwin Timestamp : 29/04/2014 16:04
 */
public final class QifCounterGuava implements QifCounter {

    private static final ConcurrentHashMultiset<String> MULTISET = ConcurrentHashMultiset.create();

    @Override
    public boolean add(String key) {
        return MULTISET.add(key);
    }

    @Override
    public int add(String key, int increment) {
        return MULTISET.add(key, increment);
    }

    @Override
    public int get(String key) {
        return MULTISET.count(key);
    }

    @Override
    public int reset(String key, int newCounter) {
        return MULTISET.setCount(key, newCounter);
    }

    @Override
    public boolean remove(String key) {
        return MULTISET.remove(key);
    }

    public int subtract(String key, int subtractCount) {
        return MULTISET.remove(key, subtractCount);
    }

    public int keySize() {
        return MULTISET.size();
    }

    @Override
    public String toString() {
        return MULTISET.toString();
    }
}
