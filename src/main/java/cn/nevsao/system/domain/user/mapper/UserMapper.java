package cn.nevsao.system.domain.user.mapper;

import cn.nevsao.common.mvc.mapper.MyMapper;
import cn.nevsao.system.domain.user.entity.User;
import cn.nevsao.system.domain.user.entity.UserWithRole;

import java.util.List;

public interface UserMapper extends MyMapper<User> {

    List<User> findUserWithDept(User user);

    List<UserWithRole> findUserWithRole(Long userId);

    User findUserProfile(User user);
}