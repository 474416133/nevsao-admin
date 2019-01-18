package cn.nevsao.system.role.service.impl;

import cn.nevsao.common.mvc.mapper.MyMapper;
import cn.nevsao.common.mvc.service.impl.ExtraService;
import cn.nevsao.common.mvc.vo.Tree;
import cn.nevsao.system.menu.entity.Menu;
import cn.nevsao.system.menu.service.MenuService;
import cn.nevsao.system.role.entity.Role;
import cn.nevsao.system.role.entity.RoleMenu;
import cn.nevsao.system.role.entity.RoleWithMenu;
import cn.nevsao.system.role.mapper.RoleMapper;
import cn.nevsao.system.role.mapper.RoleMenuMapper;
import cn.nevsao.system.role.service.RoleMenuService;
import cn.nevsao.system.role.service.RoleService;
import cn.nevsao.system.user.service.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

@Service("roleService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
@Slf4j
public class RoleServiceImpl extends ExtraService<Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private MenuService menuService;

    @Override
    public MyMapper getMapper() {
        return roleMapper;
    }

    @Override
    public List<Role> findUserRole(String userName) {
        return this.roleMapper.findUserRole(userName);
    }

    @Override
    public List<Role> all(Role role) {
        try {
            Example example = new Example(Role.class);
            if (StringUtils.isNotBlank(role.getName())) {
                example.createCriteria().andLike("name", "%"+role.getName()+"%");
            }
            example.setOrderByClause("create_time");
            return this.findByExample(example);
        } catch (Exception e) {
            log.error("获取角色信息失败", e);
            return new ArrayList<>();
        }
    }

    @Override
    public Role getByName(String roleName) {
        List<Role> list = this.findByName(Role.class, roleName);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    @Transactional
    public void insert(Role role, String[] menuIds) {
        checkName(Role.class, role.getName(), null);
        super.insert(role);
        if (menuIds == null || menuIds.length == 0){
            return;
        }
        setRoleMenus(role, menuIds);
    }

    private void setRoleMenus(Role role, String[] menuIds) {
        Arrays.stream(menuIds).forEach(menuId -> {
            RoleMenu rm = new RoleMenu();
            rm.setMenuId(menuId);
            rm.setRoleId(role.getId());
            this.roleMenuService.insert(rm);
        });
    }

    @Override
    @Transactional
    public int delete(String roleIds) {
        List<String> list = Arrays.asList(roleIds.split(","));
        int ret = this.delete(list, Role.class);
        this.roleMenuService.deleteByRoleId(roleIds);
        this.userRoleService.deleteByRoleId(roleIds);
        return ret;
    }

    @Override
    public RoleWithMenu getRoleWithMenus(String roleId) {
        List<RoleWithMenu> list = this.roleMapper.findById(roleId);
        List<String> menuList = list.stream().map(RoleWithMenu::getMenuId).collect(Collectors.toList());
        if (list.isEmpty()) {
            return null;
        }
        RoleWithMenu roleWithMenu = list.get(0);
        roleWithMenu.setMenuIds(menuList);
        return roleWithMenu;
    }

    @Override
    @Transactional
    public void update(Role role, String[] menuIds) {
        checkName(Role.class, role.getName(), role.getId());
        super.updateExcludeNull(role);
        Example example = new Example(RoleMenu.class);
        example.createCriteria().andCondition("role_id=", role.getId());
        this.roleMenuMapper.deleteByExample(example);
        if (menuIds == null || menuIds.length == 0){
            return ;
        }
        setRoleMenus(role, menuIds);
    }

    @Override
    public List<Menu> menuList(String roleId) {
        List<Menu> menus = menuService.all();
        final Set<String> roleMenuSet = new HashSet<>();
        if (roleId != null) {
            List<RoleWithMenu> roleMenus = this.roleMapper.findById(roleId);
            roleMenuSet.addAll(roleMenus.stream().map(RoleWithMenu::getMenuId).collect(Collectors.toSet()));
        }
        menus.forEach(menu -> {
            if (roleMenuSet.contains(menu.getId())){
                menu.setChecked(true);
            }
        });
         return menus;
    }
}
