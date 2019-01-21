package cn.nevsao.system.user.service;

import cn.nevsao.common.mvc.vo.QueryRequest;
import cn.nevsao.common.mvc.service.IService;
import cn.nevsao.system.user.entity.User;
import cn.nevsao.system.user.entity.UserWithRole;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@CacheConfig(cacheNames = "UserService")
public interface UserService extends IService<User> {

    User getByName(String userName);

    //@Cacheable(key = "#p0.toString() + (#p1 != null ? #p1.toString() : '')")
    List<User> findUserWithDept(User user, QueryRequest request);

    @CacheEvict(key = "#p0", allEntries = true)
    void registUser(User user);

    void updateTheme(String theme, String userName);

    @CacheEvict(allEntries = true)
    void insert(User user, String[] roles);

    @CacheEvict(key = "#p0", allEntries = true)
    void update(User user, String[] roles);

    //@CacheEvict(key = "#p0", allEntries = true)
    //int delete(String userIds);

    void updateLoginTime(String userName);

    void resetPassword(String password);

    User getUserProfile(User user);

    void updateUserProfile(User user);

    User getWithDept(String id);

    void checkUsername(String username);
    void checkEmail(String email, String id);
    void checkMobile(String mobile, String id);
}
