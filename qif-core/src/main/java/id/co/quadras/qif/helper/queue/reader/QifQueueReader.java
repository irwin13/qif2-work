package id.co.quadras.qif.helper.queue.reader;

import java.util.List;

/**
 * @author irwin Timestamp : 05/05/2014 11:44
 */
public interface QifQueueReader<T> {
    public List<T> getLogList(int maxFetch);
}
