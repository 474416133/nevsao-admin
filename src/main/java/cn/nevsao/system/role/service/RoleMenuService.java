package cn.nevsao.system.role.service;

import cn.nevsao.common.mvc.service.IService;
import cn.nevsao.common.mvc.vo.Tree;
import cn.nevsao.system.menu.entity.Menu;
import cn.nevsao.system.role.entity.RoleMenu;

import java.util.List;

public interface RoleMenuService extends IService<RoleMenu> {
	void deleteByRoleId(List<String> roleIds);
	void deleteByMenuId(List<String> menuIds);

}
