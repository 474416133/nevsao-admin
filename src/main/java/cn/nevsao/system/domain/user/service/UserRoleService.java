package cn.nevsao.system.domain.user.service;

import cn.nevsao.common.mvc.service.IService;
import cn.nevsao.system.domain.user.entity.UserRole;

public interface UserRoleService extends IService<UserRole> {

	void deleteByRoleId(String roleIds);

	void deleteByUserId(String userIds);
}
