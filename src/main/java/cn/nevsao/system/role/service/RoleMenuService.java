package cn.nevsao.system.role.service;

import cn.nevsao.common.mvc.service.IService;
import cn.nevsao.system.role.entity.RoleMenu;

public interface RoleMenuService extends IService<RoleMenu> {

	void deleteByRoleId(String roleIds);

	void deleteByMenuId(String menuIds);
}
