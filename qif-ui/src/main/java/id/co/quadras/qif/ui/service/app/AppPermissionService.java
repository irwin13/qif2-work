package id.co.quadras.qif.ui.service.app;

import com.irwin13.winwork.basic.model.UserAccess;
import com.irwin13.winwork.basic.model.UserMenu;
import com.irwin13.winwork.basic.model.entity.app.AppPermission;
import com.irwin13.winwork.basic.model.entity.app.AppRole;
import com.irwin13.winwork.basic.service.WinWorkService;

import java.util.List;

/**
 * @author irwin Timestamp : 15/04/13 14:21
 */
public interface AppPermissionService extends WinWorkService<AppPermission, String> {

    public List<AppPermission> getChildList(AppPermission parent);
    public List<AppPermission> getNullParent();

    public List<AppPermission> getMenuList(List<AppRole> appRoleList);
    public List<AppPermission> sortMenu(List<AppPermission> menuList);
    public List<AppPermission> getUserAccessList(List<AppRole> appRoleList);

    public List<AppPermission> sortMenuByLevel(List<AppPermission> parentMenu);

    public List<UserMenu> getSimpleUserMenuList(List<AppRole> appRoleList);
    public List<UserMenu> sortSimpleUserMenu(List<UserMenu> menuList);
    public List<UserAccess> getSimpleUserAccessList(List<AppRole> appRoleList);

}
