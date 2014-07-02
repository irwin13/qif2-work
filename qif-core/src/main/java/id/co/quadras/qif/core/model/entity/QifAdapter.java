package id.co.quadras.qif.core.model.entity;

import com.irwin13.winwork.basic.annotations.Searchable;
import com.irwin13.winwork.basic.annotations.Sortable;
import com.irwin13.winwork.basic.model.entity.WinWorkBasicEntity;
import com.irwin13.winwork.basic.utilities.PojoUtil;

import java.util.List;

/**
 * @author irwin Timestamp : 07/04/2014 11:53
 */
public class QifAdapter extends WinWorkBasicEntity {

    public static final String MODEL_NAME = "qifAdapter";

    @Searchable
    @Sortable
    private String name;

    @Searchable
    @Sortable
    private String adapterInterface;

    @Searchable
    @Sortable
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
