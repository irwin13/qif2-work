package id.co.quadras.qif.ui.dao.app;


import com.irwin13.winwork.basic.dao.WinWorkDao;
import com.irwin13.winwork.basic.model.entity.app.AppPermission;

import java.util.List;

/**
 * @author irwin Timestamp : 05/07/13 10:59
 */
public interface AppPermissionDao extends WinWorkDao<AppPermission, String> {
    public List<AppPermission> getChildList(AppPermission parent);
    public List<AppPermission> getNullParent();
}
