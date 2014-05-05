package id.co.quadras.qif.model.entity;

import com.irwin13.winwork.basic.model.entity.WinWorkBasicEntity;
import com.irwin13.winwork.basic.utilities.PojoUtil;

/**
 * @author irwin Timestamp : 24/04/2014 19:03
 */
public class QifAdapterProperty extends WinWorkBasicEntity {

    private String propertyKey;
    private String propertyValue;
    private String description;

    public String getPropertyKey() {
        return propertyKey;
    }

    public void setPropertyKey(String propertyKey) {
        this.propertyKey = propertyKey;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return PojoUtil.beanToString(this, true);
    }

}
