package cn.nevsao.system.domain.user.service.impl;

import cn.nevsao.common.mvc.vo.Tree;
import cn.nevsao.system.domain.user.entity.Menu;
import cn.nevsao.system.domain.user.service.MenuService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class MenuServiceImplTest {

    @Autowired
    private MenuService service;

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
        Tree<Menu> menus = service.getMenuButtonTree();
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

}