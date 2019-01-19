package cn.nevsao.system.menu.service.impl;

import cn.nevsao.common.enu.MenuTypeEnum;
import cn.nevsao.common.exception.BaseException;
import cn.nevsao.common.exception.ResponseCodeEnum;
import cn.nevsao.common.mvc.mapper.MyMapper;
import cn.nevsao.common.mvc.service.impl.ExtraService;
import cn.nevsao.common.mvc.vo.Tree;
import cn.nevsao.common.util.TreeUtils;
import cn.nevsao.system.menu.entity.Menu;
import cn.nevsao.system.menu.mapper.MenuMapper;
import cn.nevsao.system.menu.service.MenuService;
import cn.nevsao.system.role.service.RoleMenuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.*;

@Service("menuService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
@Slf4j
public class MenuServiceImpl extends ExtraService<Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private WebApplicationContext applicationContext;

    @Override
    public MyMapper getMapper() {
        return menuMapper;
    }

    @Override
    public List<Menu> findUserPermissions(String userName) {
        return this.menuMapper.findUserPermissions(userName);
    }

    @Override
    public List<Menu> findUserMenus(String userName) {
        return this.menuMapper.findUserMenus(userName);
    }

    @Override
    public List<Menu> all(Menu menu) {
        try {
            Example example = new Example(Menu.class);
            Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(menu.getName())) {
                criteria.andLike("name", "%"+menu.getName()+"%");
            }
            if (StringUtils.isNotBlank(menu.getMenuType())) {
                criteria.andCondition("menu_type=", menu.getMenuType());
            }
            example.setOrderByClause("id");
            return this.findByExample(example);
        } catch (NumberFormatException e) {
            log.error("error", e);
            return new ArrayList<>();
        }
    }

    @Override
    public Tree<Menu> getUserMenu(String userName) {
        List<Tree<Menu>> trees = new ArrayList<>();
        List<Menu> menus = this.findUserMenus(userName);
        menus.forEach(menu -> {
            Tree<Menu> tree = new Tree<>();
            tree.setId(menu.getId());
            tree.setParentId(menu.getParentId());
            tree.setText(menu.getName());
            tree.setIcon(menu.getIcon());
            tree.setUrl(menu.getUrl());
            trees.add(tree);
        });
        return TreeUtils.build(trees);
    }

    @Override
    @Transactional
    public int insert(Menu menu) {
        menu.setCreateTime(new Date());
        if (MenuTypeEnum.BUTTON.getCode().equals(menu.getMenuType())) {
            menu.setUrl(null);
            menu.setIcon(null);
        }
        return super.insert(menu);
    }

    @Override
    @Transactional
    public int delete(List<String> menuIds) {
        int ret = this.delete(menuIds, Menu.class);
        this.roleMenuService.deleteByMenuId(menuIds);
        this.menuMapper.changeToTop(menuIds);
        return ret;
    }

    @Override
    public List<Map<String, String>> getAllUrl(String p1) {
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        //获取 url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        List<Map<String, String>> urlList = new ArrayList<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : map.entrySet()) {
            RequestMappingInfo info = entry.getKey();
            HandlerMethod handlerMethod = map.get(info);
            RequiresPermissions permissions = handlerMethod.getMethodAnnotation(RequiresPermissions.class);
            String perms = "";
            if (null != permissions) {
                perms = StringUtils.join(permissions.value(), ",");
            }
            Set<String> patterns = info.getPatternsCondition().getPatterns();
            for (String url : patterns) {
                Map<String, String> urlMap = new HashMap<>();
                urlMap.put("url", url.replaceFirst("\\/", ""));
                urlMap.put("perms", perms);
                urlList.add(urlMap);
            }
        }
        return urlList;

    }

    @Override
    public List<Menu> findByNameAndType(String name, String type) {
        Example example = new Example(Menu.class);
        example.createCriteria().andCondition("lower(name)=", name.toLowerCase())
                .andEqualTo("menuType", type);
        return this.findByExample(example);
    }

    @Override
    public void checkNameAndType(String name, String type, String id) {
        List<Menu> list = findByNameAndType(name, type);
        if (list.size() > 1){
            throw new BaseException(ResponseCodeEnum.SERVER_DATA_HAD_EXIST);
        }else if (list.size() == 1 && !StringUtils.equals(list.get(0).getId(), id)){
            throw new BaseException(ResponseCodeEnum.SERVER_DATA_HAD_EXIST);
        }
    }

    @Override
    @Transactional
    public int update(Menu menu) {
        if (MenuTypeEnum.BUTTON.getCode().equals(menu.getMenuType())) {
            menu.setUrl(null);
            menu.setIcon(null);
        }
        return this.updateExcludeNull(menu);
    }

}
