package cn.nevsao.system.role.service.impl;

import cn.nevsao.common.mvc.mapper.MyMapper;
import cn.nevsao.common.mvc.service.impl.ExtraService;
import cn.nevsao.common.mvc.vo.Tree;
import cn.nevsao.system.menu.entity.Menu;
import cn.nevsao.system.menu.service.MenuService;
import cn.nevsao.system.role.entity.RoleMenu;
import cn.nevsao.system.role.mapper.RoleMenuMapper;
import cn.nevsao.system.role.service.RoleMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.management.MemoryUsage;
import java.util.Arrays;
import java.util.List;

@Service("roleMenuService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
@Slf4j
public class RoleMenuServiceImpl extends ExtraService<RoleMenu> implements RoleMenuService {

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public MyMapper getMapper() {
        return roleMenuMapper;
    }

    @Override
    @Transactional
    public void deleteByRoleId(List<String> roleIds) {
        this.delete(roleIds, "roleId", RoleMenu.class);
    }

    @Override
    @Transactional
    public void deleteByMenuId(List<String> menuIds) {
        this.delete(menuIds, "menuId", RoleMenu.class);
    }


}
