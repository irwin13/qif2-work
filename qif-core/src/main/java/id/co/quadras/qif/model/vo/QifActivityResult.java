package id.co.quadras.qif.model.vo;

import com.irwin13.winwork.basic.utilities.PojoUtil;
import id.co.quadras.qif.model.entity.log.QifActivityLogData;

import java.util.List;

/**
 * @author irwin Timestamp : 24/04/2014 20:31
 */
public class QifActivityResult {

    private final String status;
    private final Object result;

    public QifActivityResult(String status, Object result) {
        this.status = status;
        this.result = result;
    }

    private List<QifActivityLogData> logDataList;

    public String getStatus() {
        return status;
    }

    public Object getResult() {
        return result;
    }

    public List<QifActivityLogData> getLogDataList() {
        return logDataList;
    }

    public void setLogDataList(List<QifActivityLogData> logDataList) {
        this.logDataList = logDataList;
    }

    @Override
    public String toString() {
        return PojoUtil.beanToString(this, false);
    }
}
