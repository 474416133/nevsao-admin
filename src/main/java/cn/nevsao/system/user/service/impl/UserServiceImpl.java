package cn.nevsao.system.user.service.impl;

import cn.nevsao.common.exception.BaseException;
import cn.nevsao.common.exception.ResponseCodeEnum;
import cn.nevsao.common.mvc.vo.QueryRequest;
import cn.nevsao.common.enu.GenderEnum;
import cn.nevsao.common.mvc.mapper.MyMapper;
import cn.nevsao.common.mvc.service.impl.ExtraService;
import cn.nevsao.common.util.MD5Utils;
import cn.nevsao.system.dept.entity.Dept;
import cn.nevsao.system.dept.service.DeptService;
import cn.nevsao.system.user.entity.User;
import cn.nevsao.system.user.entity.UserRole;
import cn.nevsao.system.user.entity.UserWithRole;
import cn.nevsao.system.user.mapper.UserMapper;
import cn.nevsao.system.user.mapper.UserRoleMapper;
import cn.nevsao.system.user.service.UserRoleService;
import cn.nevsao.system.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    @Autowired
    private DeptService deptService;


    @Override
    public MyMapper getMapper() {
        return userMapper;
    }

    @Override
    public User getByName(String userName) {
        List<User> list = findByProperty("username", userName);
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
        user.setThemeUsing(User.DEFAULT_THEME);
        user.setAvatar(User.DEFAULT_AVATAR);
        user.setPassword("88888888");
        user.setPassword(MD5Utils.encrypt(user.getUsername(), user.getPassword()));
        //this.save(user);
        super.insert(user);
        setUserRoles(user, roles);
    }

    private void setUserRoles(User user, String[] roles) {
        Arrays.stream(roles).forEach(roleId -> {
            UserRole ur = new UserRole();
            ur.setUserId(user.getId());
            ur.setRoleId(roleId);
            this.userRoleService.insert(ur);
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
    public int delete(List<String> userIds) {
        int ret = this.delete(userIds, "userId", User.class);
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

    @Override
    public User getWithDept(String id) {
        User user = get(id);
        if (StringUtils.isNotBlank(user.getDeptId())){
            Dept dept = deptService.get(user.getDeptId());
            user.setDeptName(dept.getName());
        }
        return user;
    }

    @Override
    public void checkUsername(String username) {
        checkList(findByProperty("username", username), null);
    }

    @Override
    public void checkEmail(String email, String id) {
        checkList(findByProperty("email", email), id);

    }

    @Override
    public void checkMobile(String mobile, String id) {
        checkList(findByProperty("mobile", mobile), id);
    }

    private List<User> findByProperty(String propertyName, Object value){
        Example example = new Example(User.class);
        example.createCriteria().andCondition("lower("+propertyName+")=", value);
        return this.findByExample(example);
    }

    private void checkList(List<User> list, String id){
        if (list.size() > 1){
            throw  new BaseException(ResponseCodeEnum.SERVER_DATA_HAD_EXIST);
        }else if (list.size() == 1){
            if (!StringUtils.equals(id, list.get(0).getId())){
                throw new BaseException(ResponseCodeEnum.SERVER_DATA_HAD_EXIST);
            }
        }
    }

}
