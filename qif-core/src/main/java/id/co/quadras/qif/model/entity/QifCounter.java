package id.co.quadras.qif.model.entity;

import com.irwin13.winwork.basic.model.entity.WinWorkBasicEntity;
import com.irwin13.winwork.basic.utilities.PojoUtil;

/**
 * @author irwin Timestamp : 04/06/2014 18:49
 */
public class QifCounter extends WinWorkBasicEntity {

    private String sequenceKey;
    private Integer lastSequence;

    public String getSequenceKey() {
        return sequenceKey;
    }

    public void setSequenceKey(String sequenceKey) {
        this.sequenceKey = sequenceKey;
    }

    public Integer getLastSequence() {
        return lastSequence;
    }

    public void setLastSequence(Integer lastSequence) {
        this.lastSequence = lastSequence;
    }

    @Override
    public String toString() {
        return PojoUtil.beanToString(this, true);
    }
}
