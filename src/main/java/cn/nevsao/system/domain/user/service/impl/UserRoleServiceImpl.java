package cn.nevsao.system.domain.user.service.impl;

import cn.nevsao.common.mvc.service.impl.BaseService;
import cn.nevsao.system.domain.user.entity.UserRole;
import cn.nevsao.system.domain.user.service.UserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service("userRoleService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserRoleServiceImpl extends BaseService<UserRole> implements UserRoleService {

	@Override
	@Transactional
	public void deleteByRoleId(String roleIds) {
		List<String> list = Arrays.asList(roleIds.split(","));
		this.delete(list, "roleId", UserRole.class);
	}

	@Override
	@Transactional
	public void deleteByUserId(String userIds) {
		List<String> list = Arrays.asList(userIds.split(","));
		this.delete(list, "userId", UserRole.class);
	}

}
