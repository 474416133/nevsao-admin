package cn.nevsao.system.domain.user.service.impl;

import cn.nevsao.common.mvc.service.impl.BaseService;
import cn.nevsao.system.domain.user.entity.RoleMenu;
import cn.nevsao.system.domain.user.service.RoleMenuServie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service("roleMenuService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
@Slf4j
public class RoleMenuServiceImpl extends BaseService<RoleMenu> implements RoleMenuServie {

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
