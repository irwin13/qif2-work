package id.co.quadras.qif.model.vo;

import com.irwin13.winwork.basic.utilities.PojoUtil;

import java.util.Map;

/**
 * @author irwin Timestamp : 24/04/2014 20:31
 */
public class QifEventResult {

    private final String status;
    private final Object result;

    public QifEventResult(String status, Object result) {
        this.status = status;
        this.result = result;
    }

    private Map<String, Object> eventData;

    public String getStatus() {
        return status;
    }

    public Object getResult() {
        return result;
    }

    public Map<String, Object> getEventData() {
        return eventData;
    }

    public void setEventData(Map<String, Object> eventData) {
        this.eventData = eventData;
    }

    @Override
    public String toString() {
        return PojoUtil.beanToString(this, false);
    }
}
