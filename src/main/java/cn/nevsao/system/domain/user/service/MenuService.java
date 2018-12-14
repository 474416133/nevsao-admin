package cn.nevsao.system.domain.user.service;

import cn.nevsao.common.domain.Tree;
import cn.nevsao.common.mvc.service.IService;
import cn.nevsao.system.domain.user.entity.Menu;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Map;

@CacheConfig(cacheNames = "MenuService")
public interface MenuService extends IService<Menu> {

    List<Menu> findUserPermissions(String userName);

    List<Menu> findUserMenus(String userName);

    List<Menu> all(Menu menu);

    Tree<Menu> getMenuButtonTree();

    Tree<Menu> getMenuTree();

    Tree<Menu> getUserMenu(String userName);

    Menu get(String menuId);

    Menu getByNameAndType(String menuName, String type);

    @Override
    int insert(Menu menu);

    @Override
    int update(Menu menu);


    @Cacheable(key = "'url_'+ #p0")
    List<Map<String, String>> getAllUrl(String p1);
}
