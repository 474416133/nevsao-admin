package cn.nevsao.system.domain.user.service;

import cn.nevsao.common.mvc.service.IService;
import cn.nevsao.system.domain.user.entity.RoleMenu;

public interface RoleMenuServie extends IService<RoleMenu> {

	void deleteByRoleId(String roleIds);

	void deleteByMenuId(String menuIds);
}
