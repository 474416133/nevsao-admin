package cn.nevsao.system.user.service.impl;

import cn.nevsao.common.mvc.mapper.MyMapper;
import cn.nevsao.common.mvc.service.impl.ExtraService;
import cn.nevsao.system.user.entity.UserRole;
import cn.nevsao.system.user.mapper.UserRoleMapper;
import cn.nevsao.system.user.service.UserRoleService;
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
    public void deleteByRoleId(List<String> roleIds) {
        this.delete(roleIds, "roleId", UserRole.class);
    }

    @Override
    @Transactional
    public void deleteByUserId(List<String> userIds) {
        this.delete(userIds, "userId", UserRole.class);
    }

}
