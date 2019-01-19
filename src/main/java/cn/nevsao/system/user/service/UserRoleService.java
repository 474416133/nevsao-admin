package cn.nevsao.system.user.service;

import cn.nevsao.common.mvc.service.IService;
import cn.nevsao.system.user.entity.UserRole;

import java.util.List;

public interface UserRoleService extends IService<UserRole> {

	void deleteByRoleId(List<String> roleIds);

	void deleteByUserId(List<String> userIds);
}
