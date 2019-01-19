package cn.nevsao.system.menu.service;

import cn.nevsao.common.mvc.vo.Tree;
import cn.nevsao.common.mvc.service.IService;
import cn.nevsao.system.menu.entity.Menu;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Map;

@CacheConfig(cacheNames = "MenuService")
public interface MenuService extends IService<Menu> {

    List<Menu> findUserPermissions(String userName);

    List<Menu> findUserMenus(String userName);

    List<Menu> all(Menu menu);

    Tree<Menu> getUserMenu(String userName);

    @Override
    Menu get(String menuId);

    @Override
    int insert(Menu menu);

    @Override
    int update(Menu menu);

    //@Cacheable(key = "'url_'+ #p0")
    List<Map<String, String>> getAllUrl(String p1);

    /**
     * 根据名字和类型查询
     * @param name
     * @param type
     * @return
     */
    List<Menu> findByNameAndType(String name, String type);

    /**
     * 校验
     * @param name
     * @param type
     */
    void checkNameAndType(String name, String type, String id);
}
