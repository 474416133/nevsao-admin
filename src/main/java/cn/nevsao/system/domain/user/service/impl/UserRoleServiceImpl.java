package cn.nevsao.system.domain.user.service.impl;

import cn.nevsao.common.mvc.mapper.MyMapper;
import cn.nevsao.common.mvc.service.impl.ExtraService;
import cn.nevsao.system.domain.user.entity.UserRole;
import cn.nevsao.system.domain.user.mapper.UserRoleMapper;
import cn.nevsao.system.domain.user.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service("userRoleService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserRoleServiceImpl extends ExtraService<UserRole> implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public MyMapper getMapper() {
        return userRoleMapper;
    }

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
