package cn.nevsao.system.user.service;

import cn.nevsao.common.mvc.service.IService;
import cn.nevsao.system.user.entity.UserRole;

public interface UserRoleService extends IService<UserRole> {

	void deleteByRoleId(String roleIds);

	void deleteByUserId(String userIds);
}
