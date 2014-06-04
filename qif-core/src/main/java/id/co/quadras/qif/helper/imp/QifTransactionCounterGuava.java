package id.co.quadras.qif.helper.imp;

import com.google.common.collect.ConcurrentHashMultiset;
import id.co.quadras.qif.helper.QifTransactionCounter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author irwin Timestamp : 29/04/2014 16:04
 */
public final class QifTransactionCounterGuava implements QifTransactionCounter {

    private static final Logger LOGGER = LoggerFactory.getLogger(QifTransactionCounterGuava.class);
    private static final ConcurrentHashMultiset<String> MULTISET = ConcurrentHashMultiset.create();

    @Override
    public boolean add(String key) {
        LOGGER.trace("add key = {}", key);
        return MULTISET.add(key);
    }

    @Override
    public int add(String key, int increment) {
        LOGGER.trace("add key = {} with increment = {}", key, increment);
        return MULTISET.add(key, increment);
    }

    @Override
    public int get(String key) {
        LOGGER.trace("get key = {} ", key);
        return MULTISET.count(key);
    }

    @Override
    public int reset(String key, int newCounter) {
        LOGGER.trace("reset key = {} with newCounter = {}", key, newCounter);
        return MULTISET.setCount(key, newCounter);
    }

    @Override
    public boolean remove(String key) {
        LOGGER.trace("remove key = {}", key);
        return MULTISET.remove(key);
    }

    public int subtract(String key, int subtractCount) {
        LOGGER.trace("subtract key = {} with value = {}", key, subtractCount);
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
