package id.co.quadras.qif.ui.service.app.imp;

import com.google.inject.Inject;
import com.irwin13.winwork.basic.model.SearchParameter;
import com.irwin13.winwork.basic.model.SortParameter;
import com.irwin13.winwork.basic.model.UserAccess;
import com.irwin13.winwork.basic.model.UserMenu;
import com.irwin13.winwork.basic.model.entity.app.AppPermission;
import com.irwin13.winwork.basic.model.entity.app.AppRole;
import com.irwin13.winwork.basic.service.BasicEntityCommonService;
import id.co.quadras.qif.ui.dao.app.AppPermissionDao;
import id.co.quadras.qif.ui.service.app.AppPermissionService;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author irwin Timestamp : 15/04/13 14:39
 */
public class AppPermissionServiceImp implements AppPermissionService {

    private final BasicEntityCommonService commonService;
    private final AppPermissionDao dao;

    @Inject
    public AppPermissionServiceImp(BasicEntityCommonService commonService, AppPermissionDao dao) {
        this.commonService = commonService;
        this.dao = dao;
    }

    @Override
    public List<AppPermission> select(AppPermission filter, SortParameter sortParameter) {
        commonService.onSelect(filter);
        return dao.select(filter, sortParameter);
    }

    @Override
    public List<AppPermission> select(AppPermission filter, SortParameter sortParameter, int start, int size) {
        commonService.onSelect(filter);
        return dao.select(filter, sortParameter, start, size);
    }

    @Override
    public long selectCount(AppPermission filter) {
        commonService.onSelect(filter);
        return dao.selectCount(filter);
    }

    @Override
    public List<AppPermission> selectSearch(SearchParameter searchParameter) {
        return dao.selectSearch(searchParameter);
    }

    @Override
    public List<AppPermission> selectSearch(SearchParameter searchParameter, int start, int size) {
        return dao.selectSearch(searchParameter, start, size);
    }

    @Override
    public long selectSearchCount(String searchKeyword) {
        return dao.selectSearchCount(new SearchParameter(searchKeyword, null, null));
    }

    @Override
    public AppPermission getById(String id, boolean init) {
        return dao.getById(id, init);
    }

    @Override
    public String insert(AppPermission model) {
        commonService.onInsert(model);
        if (model.getMenuOrder() == null) {
            model.setMenuOrder(0);
        }
        return dao.insert(model);
    }

    @Override
    public void update(AppPermission model) {
        commonService.onUpdate(model);
        if (model.getMenuOrder() == null) {
            model.setMenuOrder(0);
        }
        dao.merge(model);
    }

    @Override
    public void softDelete(AppPermission model) {
        commonService.onSoftDelete(model);
        if (model.getMenuOrder() == null) {
            model.setMenuOrder(0);
        }
        dao.merge(model);
    }

    @Override
    public void insertOrUpdate(AppPermission model) {
        commonService.onInsertOrUpdate(model) ;
        if (model.getMenuOrder() == null) {
            model.setMenuOrder(0);
        }
        dao.saveOrUpdate(model);
    }

    @Override
    public List<AppPermission> getChildList(AppPermission parent) {
        return dao.getChildList(parent);
    }

    @Override
    public List<AppPermission> getNullParent() {
        return dao.getNullParent();
    }

    @Override
    public List<AppPermission> getMenuList(List<AppRole> appRoleList) {
        List<AppPermission> menuList = null;

        if (appRoleList != null && !appRoleList.isEmpty()) {
            menuList = new LinkedList<AppPermission>();
            for (AppRole appRole : appRoleList) {
                List<AppPermission> subMenuList = appRole.getAppPermissionList();
                if (subMenuList != null) {
                    for (AppPermission appPermission : subMenuList) {
                        if ((appPermission.getAsMenu() != null && appPermission.getAsMenu())
                                && !menuList.contains(appPermission)) {
                            menuList.add(appPermission);
                        }
                    }
                }
            }
        }

        if (menuList != null) {
            Collections.sort(menuList, new Comparator<AppPermission>() {
                @Override
                public int compare(AppPermission o1, AppPermission o2) {
                    int result = 0;
                    if (o1.getMenuOrder() > o2.getMenuOrder()) {
                        result = 1;
                    } else if (o1.getMenuOrder() < o2.getMenuOrder()) {
                        result = -1;
                    } else if (o1.getMenuOrder() == o2.getMenuOrder()) {
                        result = 0;
                    }
                    return result;
                }
            });
        } else {
            menuList = Collections.emptyList();
        }

        return menuList;
    }

    @Override
    public List<AppPermission> sortMenu(List<AppPermission> menuList) {
        List<AppPermission> orderList = new LinkedList<AppPermission>();
        for (AppPermission menu : menuList) {
            addChild(menuList, menu);
            if (menu.getParentMenu() == null)
                orderList.add(menu);
        }
        return orderList;
    }

    private void addChild(List<AppPermission> menuList, AppPermission menu) {
        List<AppPermission> childList = new LinkedList<AppPermission>();
        for (AppPermission menu2 : menuList) {
            if (menu2.getParentMenu() != null && menu2.getParentMenu().equals(menu)) {
                childList.add(menu2);
                addChild(menuList, menu2);
            }

            if (childList.size() > 0)
                menu.setChildMenuList(childList);
        }
    }

    @Override
    public List<AppPermission> getUserAccessList(List<AppRole> appRoleList) {
        List<AppPermission> menuList = null;

        if (appRoleList != null && !appRoleList.isEmpty()) {
            menuList = new LinkedList<AppPermission>();
            for (AppRole appRole : appRoleList) {
                List<AppPermission> subMenuList = appRole.getAppPermissionList();
                if (subMenuList != null) {
                    for (AppPermission appPermission : subMenuList) {
                        if (!menuList.contains(appPermission)) {
                            menuList.add(appPermission);
                        }
                    }
                }
            }
        }

        if (menuList != null) {
            Collections.sort(menuList, new Comparator<AppPermission>() {
                @Override
                public int compare(AppPermission o1, AppPermission o2) {
                    int result = 0;
                    if (o1.getMenuOrder() > o2.getMenuOrder()) {
                        result = 1;
                    } else if (o1.getMenuOrder() < o2.getMenuOrder()) {
                        result = -1;
                    } else if (o1.getMenuOrder() == o2.getMenuOrder()) {
                        result = 0;
                    }
                    return result;
                }
            });
        } else {
            menuList = Collections.emptyList();
        }

        return menuList;
    }

    @Override
    public List<AppPermission> sortMenuByLevel(List<AppPermission> parentMenu) {
        List<AppPermission> result = new LinkedList<AppPermission>();

        for (AppPermission menu : parentMenu) {
            if (!menu.getName().equalsIgnoreCase("Home")) result.add(menu);
            getChildMenu(result, 0, menu);
        }

        return result;
    }

    private void getChildMenu(List<AppPermission> result, int level, AppPermission parent) {

        List<AppPermission> childList = getChildList(parent);
        if (childList != null) {
            int count = 5;
            for (AppPermission menu : childList) {
                StringBuilder sb = new StringBuilder();
                sb.delete(0, sb.length());
                for(int i =0 ; i < (level + count); i++){
                    sb.append("&nbsp;");
                }
                menu.setTab(sb.toString());
                result.add(menu);
                level++;
                getChildMenu(result, level, menu);
                level--;
            }
        }
    }

    public List<UserMenu> getSimpleUserMenuList(List<AppRole> appRoleList) {
        List<UserMenu> menuList = null;

        if (appRoleList != null && !appRoleList.isEmpty()) {
            menuList = new LinkedList<UserMenu>();
            for (AppRole appRole : appRoleList) {
                List<AppPermission> subMenuList = appRole.getAppPermissionList();
                if (subMenuList != null) {
                    for (AppPermission appPermission : subMenuList) {
                        if (appPermission != null) {
                            UserMenu userMenu = new UserMenu();

                            userMenu.setId(appPermission.getId());
                            userMenu.setAsMenu(appPermission.getAsMenu());
                            userMenu.setHttpPath(appPermission.getHttpPath());
                            userMenu.setName(appPermission.getName());
                            userMenu.setMenuOrder(appPermission.getMenuOrder());

                            AppPermission parentMenuPermission = appPermission.getParentMenu();
                            if (parentMenuPermission != null) {
                                UserMenu parentMenu = new UserMenu();
                                parentMenu.setAsMenu(parentMenuPermission.getAsMenu());
                                parentMenu.setHttpPath(parentMenuPermission.getHttpPath());
                                parentMenu.setId(parentMenuPermission.getId());
                                parentMenu.setMenuOrder(parentMenuPermission.getMenuOrder());
                                parentMenu.setName(parentMenuPermission.getName());
                                userMenu.setParentMenu(parentMenu);
                            }

                            if ((userMenu.isAsMenu() != null && userMenu.isAsMenu()) && !menuList.contains(userMenu)) {
                                menuList.add(userMenu);
                            }
                        }
                    }
                }
            }
        }

        if (menuList != null) {
            Collections.sort(menuList, new Comparator<UserMenu>() {
                @Override
                public int compare(UserMenu o1, UserMenu o2) {
                    int result = 0;
                    if (o1.getMenuOrder() > o2.getMenuOrder()) {
                        result = 1;
                    } else if (o1.getMenuOrder() < o2.getMenuOrder()) {
                        result = -1;
                    } else if (o1.getMenuOrder() == o2.getMenuOrder()) {
                        result = 0;
                    }
                    return result;
                }
            });
        } else {
            menuList = Collections.emptyList();
        }

        return menuList;
    }

    @Override
    public List<UserMenu> sortSimpleUserMenu(List<UserMenu> menuList) {
        List<UserMenu> orderList = new LinkedList<UserMenu>();
        for (UserMenu menu : menuList) {
            addChildSimpleUserMenu(menuList, menu);
            if (menu.getParentMenu() == null)
                orderList.add(menu);
        }
        return orderList;

    }

    private void addChildSimpleUserMenu(List<UserMenu> menuList, UserMenu menu) {
        List<UserMenu> childList = new LinkedList<UserMenu>();
        for (UserMenu menu2 : menuList) {
            if (menu2.getParentMenu() != null && menu2.getParentMenu().equals(menu)) {
                childList.add(menu2);
                addChildSimpleUserMenu(menuList, menu2);
            }

            if (childList.size() > 0)
                menu.setChildMenuList(childList);
        }
    }

    @Override
    public List<UserAccess> getSimpleUserAccessList(List<AppRole> appRoleList) {
        List<UserAccess> menuList = null;

        if (appRoleList != null && !appRoleList.isEmpty()) {
            menuList = new LinkedList<UserAccess>();
            for (AppRole appRole : appRoleList) {
                List<AppPermission> subMenuList = appRole.getAppPermissionList();
                if (subMenuList != null) {
                    for (AppPermission appPermission : subMenuList) {
                        if (appPermission != null) {
                            UserAccess userAccess = new UserAccess();
                            userAccess.setId(appPermission.getId());
                            userAccess.setHttpMethod(appPermission.getHttpMethod());
                            userAccess.setHttpPath(appPermission.getHttpPath());
                            userAccess.setMenuOrder(appPermission.getMenuOrder());

                            if (!menuList.contains(appPermission)) {
                                menuList.add(userAccess);
                            }
                        }
                    }
                }
            }
        }

        if (menuList != null) {
            Collections.sort(menuList, new Comparator<UserAccess>() {
                @Override
                public int compare(UserAccess o1, UserAccess o2) {
                    int result = 0;
                    if (o1.getMenuOrder() > o2.getMenuOrder()) {
                        result = 1;
                    } else if (o1.getMenuOrder() < o2.getMenuOrder()) {
                        result = -1;
                    } else if (o1.getMenuOrder() == o2.getMenuOrder()) {
                        result = 0;
                    }
                    return result;
                }
            });
        } else {
            menuList = Collections.emptyList();
        }

        return menuList;
    }

}
