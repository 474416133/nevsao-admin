package cn.nevsao.system.domain.user.service.impl;

import cn.nevsao.common.mvc.vo.QueryRequest;
import cn.nevsao.common.enu.GenderEnum;
import cn.nevsao.common.mvc.mapper.MyMapper;
import cn.nevsao.common.mvc.service.impl.ExtraService;
import cn.nevsao.common.util.MD5Utils;
import cn.nevsao.system.domain.user.entity.User;
import cn.nevsao.system.domain.user.entity.UserRole;
import cn.nevsao.system.domain.user.entity.UserWithRole;
import cn.nevsao.system.domain.user.mapper.UserMapper;
import cn.nevsao.system.domain.user.mapper.UserRoleMapper;
import cn.nevsao.system.domain.user.service.UserRoleService;
import cn.nevsao.system.domain.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service("userService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
@Slf4j
public class UserServiceImpl extends ExtraService<User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public MyMapper getMapper() {
        return userMapper;
    }

    @Override
    public User getByName(String userName) {
        Example example = new Example(User.class);
        example.createCriteria().andCondition("lower(username)=", userName.toLowerCase());
        List<User> list = this.findByExample(example);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<User> findUserWithDept(User user, QueryRequest request) {
        try {
            return this.userMapper.findUserWithDept(user);
        } catch (Exception e) {
            log.error("error", e);
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional
    public void registUser(User user) {
        user.setCreateTime(new Date());
        user.setThemeUsing(User.DEFAULT_THEME);
        user.setAvatar(User.DEFAULT_AVATAR);
        user.setGender(GenderEnum.SECRET.getCode());
        user.setPassword(MD5Utils.encrypt(user.getUsername(), user.getPassword()));
        super.insert(user);
        //this.save(user);
        //UserRole ur = new UserRole();
        //ur.setUserId(user.getId());
        //ur.setRoleId(3L);
        //this.userRoleMapper.insert(ur);
    }

    @Override
    @Transactional
    public void updateTheme(String theme, String userName) {
        Example example = new Example(User.class);
        example.createCriteria().andCondition("username=", userName);
        User user = new User();
        user.setThemeUsing(theme);
        this.userMapper.updateByExampleSelective(user, example);
    }

    @Override
    @Transactional
    public void insert(User user, String[] roles) {
        user.setCreateTime(new Date());
        user.setThemeUsing(User.DEFAULT_THEME);
        user.setAvatar(User.DEFAULT_AVATAR);
        user.setPassword(MD5Utils.encrypt(user.getUsername(), user.getPassword()));
        //this.save(user);
        super.insert(user);
        setUserRoles(user, roles);
    }

    private void setUserRoles(User user, String[] roles) {
        Arrays.stream(roles).forEach(roleId -> {
            UserRole ur = new UserRole();
            ur.setId(user.getId());
            ur.setRoleId(roleId);
            this.userRoleMapper.insert(ur);
        });
    }

    @Override
    @Transactional
    public void update(User user, String[] roles) {
        user.setPassword(null);
        user.setUsername(null);
        user.setModifyTime(new Date());
        this.updateExcludeNull(user);
        Example example = new Example(UserRole.class);
        example.createCriteria().andCondition("id=", user.getId());
        this.userRoleMapper.deleteByExample(example);
        setUserRoles(user, roles);
    }

    @Override
    @Transactional
    public int delete(String userIds) {
        List<String> list = Arrays.asList(userIds.split(","));
        int ret = this.delete(list, "userId", User.class);
        this.userRoleService.deleteByUserId(userIds);
        return ret;
    }

    @Override
    @Transactional
    public void updateLoginTime(String userName) {
        Example example = new Example(User.class);
        example.createCriteria().andCondition("lower(username)=", userName.toLowerCase());
        User user = new User();
        user.setLastLoginTime(new Date());
        this.userMapper.updateByExampleSelective(user, example);
    }

    @Override
    @Transactional
    public void resetPassword(String password) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Example example = new Example(User.class);
        example.createCriteria().andCondition("username=", user.getUsername());
        String newPassword = MD5Utils.encrypt(user.getUsername().toLowerCase(), password);
        user.setPassword(newPassword);
        this.userMapper.updateByExampleSelective(user, example);

    }

    @Override
    public UserWithRole getWithRole(Long userId) {
        List<UserWithRole> list = this.userMapper.findUserWithRole(userId);
        List<Long> roleList = list.stream().map(UserWithRole::getRoleId).collect(Collectors.toList());
        if (list.isEmpty()) {
            return null;
        }
        UserWithRole userWithRole = list.get(0);
        userWithRole.setRoleIds(roleList);
        return userWithRole;
    }

    @Override
    public User getUserProfile(User user) {
        return this.userMapper.findUserProfile(user);
    }

    @Override
    @Transactional
    public void updateUserProfile(User user) {
        user.setUsername(null);
        user.setPassword(null);
        this.updateExcludeNull(user);
    }

}
