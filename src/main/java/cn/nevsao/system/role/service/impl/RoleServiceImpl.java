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
import org.apache.poi.ss.formula.functions.T;
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
    public int delete(List<String> ids) {
        int ret = this.delete(ids, Role.class);
        this.roleMenuService.deleteByRoleId(ids);
        this.userRoleService.deleteByRoleId(ids);
        return ret;
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
