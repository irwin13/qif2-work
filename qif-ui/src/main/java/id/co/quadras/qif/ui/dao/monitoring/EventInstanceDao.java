package id.co.quadras.qif.ui.dao.monitoring;

import id.co.quadras.qif.ui.dto.monitoring.EventMsg;
import java.util.List;

/**
 * @author irwin Timestamp : 07/07/2014 17:18
 */
public interface EventInstanceDao {
    public List selectEventInstance(int start, int fetchSize);
    public long selectCountEventInstance();
    public EventMsg getEventMsg(String id);
}
