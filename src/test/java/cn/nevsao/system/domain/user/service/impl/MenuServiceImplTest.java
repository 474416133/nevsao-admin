package cn.nevsao.system.user.service.impl;

import cn.nevsao.common.mvc.vo.Tree;
import cn.nevsao.system.menu.entity.Menu;
import cn.nevsao.system.menu.service.MenuService;
import cn.nevsao.system.user.entity.User;
import cn.nevsao.system.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MenuServiceImplTest {

    @Autowired
    private MenuService service;

    @Autowired
    private UserService userService;

    @Test
    public void findUserPermissions() throws Exception {
    }

    @Test
    public void findUserMenus() throws Exception {
    }

    @Test
    public void all() throws Exception {
    }

    @Test
    public void getMenuButtonTree() throws Exception {
        Tree<Menu> menus = service.getUserMenu("Mrbird");
        System.out.println("ok");

    }

    @Test
    public void getMenuTree() throws Exception {
    }

    @Test
    public void getUserMenu() throws Exception {
    }

    @Test
    public void getByNameAndType() throws Exception {
    }

    @Test
    public void getAllUrl() throws Exception {
    }

    @Test
    public void findUserWithDept() throws Exception {
        User user = new User();
        List<User>  users = userService.findUserWithDept(user, null);
        System.out.println("ok");
    }

}