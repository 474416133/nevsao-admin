package cn.nevsao.system.domain.user.mapper;

import cn.nevsao.common.mvc.mapper.MyMapper;
import cn.nevsao.system.domain.user.entity.Role;
import cn.nevsao.system.domain.user.entity.RoleWithMenu;

import java.util.List;

public interface RoleMapper extends MyMapper<Role> {

    List<Role> findUserRole(String userName);

    List<RoleWithMenu> findById(String roleId);
}