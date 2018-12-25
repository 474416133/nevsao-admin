package cn.nevsao.system.user.mapper;

import cn.nevsao.common.mvc.mapper.MyMapper;
import cn.nevsao.system.user.entity.User;
import cn.nevsao.system.user.entity.UserWithRole;

import java.util.List;

public interface UserMapper extends MyMapper<User> {

    List<User> findUserWithDept(User user);

    List<UserWithRole> findUserWithRole(Long userId);

    User findUserProfile(User user);
}