package cn.nevsao.system.role.service;

import cn.nevsao.common.mvc.service.IService;
import cn.nevsao.common.mvc.vo.Tree;
import cn.nevsao.system.menu.entity.Menu;
import cn.nevsao.system.role.entity.Role;
import cn.nevsao.system.role.entity.RoleWithMenu;

import java.util.List;

public interface RoleService extends IService<Role> {

	List<Role> findUserRole(String userName);

	List<Role> all(Role role);

	void insert(Role role, String[] menuIds);

	void update(Role role, String[] menuIds);

	//加载所有菜单并根据roleId标记已选
	List<Menu> menuList(String roleId);

	/**
	 * 根据userId设置flag属性并返回全部
	 * @param userId
	 * @return
	 */
	List<Role> allWithFlagByUser(String userId);



}
