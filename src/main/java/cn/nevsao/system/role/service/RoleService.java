package cn.nevsao.system.role.service;

import cn.nevsao.common.mvc.service.IService;
import cn.nevsao.system.role.entity.Role;
import cn.nevsao.system.role.entity.RoleWithMenu;

import java.util.List;

public interface RoleService extends IService<Role> {

	List<Role> findUserRole(String userName);

	List<Role> all(Role role);
	
	RoleWithMenu getRoleWithMenus(String roleId);

	Role getByName(String roleName);

	void insert(Role role, String[] menuIds);
	
	void update(Role role, String[] menuIds);

}
