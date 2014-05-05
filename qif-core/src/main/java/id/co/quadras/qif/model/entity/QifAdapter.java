package id.co.quadras.qif.model.entity;

import com.irwin13.winwork.basic.model.entity.WinWorkBasicEntity;
import com.irwin13.winwork.basic.utilities.PojoUtil;

import java.util.List;

/**
 * @author irwin Timestamp : 07/04/2014 11:53
 */
public class QifAdapter extends WinWorkBasicEntity {

    private String name;
    private String adapterInterface;
    private String description;

    private List<QifAdapterProperty> qifAdapterPropertyList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdapterInterface() {
        return adapterInterface;
    }

    public void setAdapterInterface(String adapterInterface) {
        this.adapterInterface = adapterInterface;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<QifAdapterProperty> getQifAdapterPropertyList() {
        return qifAdapterPropertyList;
    }

    public void setQifAdapterPropertyList(List<QifAdapterProperty> qifAdapterPropertyList) {
        this.qifAdapterPropertyList = qifAdapterPropertyList;
    }

    @Override
    public String toString() {
        return PojoUtil.beanToString(this, true);
    }
}
