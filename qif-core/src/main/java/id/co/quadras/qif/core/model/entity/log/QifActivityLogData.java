package id.co.quadras.qif.core.model.entity.log;

import com.irwin13.winwork.basic.model.entity.WinWorkBasicEntity;
import com.irwin13.winwork.basic.utilities.PojoUtil;

/**
 * @author irwin Timestamp : 24/04/2014 19:34
 */
public class QifActivityLogData extends WinWorkBasicEntity {

    private String activityLogId;
    private String dataKey;
    private String dataValue;

    public String getActivityLogId() {
        return activityLogId;
    }

    public void setActivityLogId(String activityLogId) {
        this.activityLogId = activityLogId;
    }

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    public String getDataValue() {
        return dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }

    @Override
    public String toString() {
        return PojoUtil.beanToString(this, true);
    }

}
