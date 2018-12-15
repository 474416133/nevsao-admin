package cn.nevsao.system.domain.user.service.impl;

import cn.nevsao.common.mvc.mapper.MyMapper;
import cn.nevsao.common.mvc.service.impl.ExtraService;
import cn.nevsao.system.domain.user.entity.RoleMenu;
import cn.nevsao.system.domain.user.mapper.RoleMenuMapper;
import cn.nevsao.system.domain.user.service.RoleMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    public void deleteByRoleId(String roleIds) {
        List<String> list = Arrays.asList(roleIds.split(","));
        this.delete(list, "roleId", RoleMenu.class);
    }

    @Override
    @Transactional
    public void deleteByMenuId(String menuIds) {
        List<String> list = Arrays.asList(menuIds.split(","));
        this.delete(list, "menuId", RoleMenu.class);
    }

}
