package id.co.quadras.qif.model.vo;

import com.irwin13.winwork.basic.utilities.PojoUtil;

import java.util.Map;

/**
 * @author irwin Timestamp : 24/04/2014 20:31
 */
public class QifActivityResult {

    private final String status;
    private final Object result;
    private final Map<String, String> additionalData;

    public QifActivityResult(String status, Object result, Map<String, String> additionalData) {
        this.status = status;
        this.result = result;
        this.additionalData = additionalData;
    }

    public String getStatus() {
        return status;
    }

    public Object getResult() {
        return result;
    }

    public Map<String, String> getAdditionalData() {
        return additionalData;
    }

    @Override
    public String toString() {
        return PojoUtil.beanToString(this, false);
    }
}
