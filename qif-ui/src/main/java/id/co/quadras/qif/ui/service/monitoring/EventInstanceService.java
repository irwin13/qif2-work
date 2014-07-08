package id.co.quadras.qif.ui.service.monitoring;

import id.co.quadras.qif.ui.dto.monitoring.EventMsg;

import java.util.List;

/**
 * @author irwin Timestamp : 08/07/2014 14:20
 */
public interface EventInstanceService {
    public List selectEventInstance(int start, int fetchSize);
    public long selectCountEventInstance();
    public EventMsg getEventMsg(String id);
}
