package cn.nevsao.system.menu.controller.v1;

import cn.nevsao.common.annotation.Log;
import cn.nevsao.common.enu.MenuTypeEnum;
import cn.nevsao.common.mvc.vo.ResponseBo;
import cn.nevsao.common.mvc.vo.Tree;
import cn.nevsao.common.mvc.controller.BaseController;
import cn.nevsao.common.util.FileUtil;
import cn.nevsao.system.menu.entity.Menu;
import cn.nevsao.system.menu.service.MenuService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "system/")
public class MenuController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String PATH_PREFIX = "system/menu/";

    @Autowired
    private MenuService menuService;

    @Log("获取菜单信息")
    @RequestMapping("menu")
    @RequiresPermissions("system:menu:list")
    public String index() {
        return PATH_PREFIX + "menu";
    }

    @RequestMapping("menu/menu")
    @ResponseBody
    public ResponseBo getMenuByUsername(String userName) {
        List<Menu> menus = this.menuService.findUserMenus(userName);
        return ResponseBo.ok(menus);
    }

    @RequestMapping("menu/getMenu")
    @ResponseBody
    public ResponseBo getMenu(String menuId) {

        Menu menu = this.menuService.get(menuId);
        return ResponseBo.ok(menu);
    }

    @RequestMapping("menu/menuButtonTree")
    @ResponseBody
    public ResponseBo getMenuButtonTree() {

        Tree<Menu> tree = this.menuService.getMenuButtonTree();
        return ResponseBo.ok(tree);
    }

    @RequestMapping("menu/tree/view")
    public String getMenuTreeVew() {
        return PATH_PREFIX + "tree";
    }

    @RequestMapping("menu/tree")
    @ResponseBody
    public ResponseBo getMenuTree() {
        try {
            Tree<Menu> tree = this.menuService.getMenuTree();
            return ResponseBo.ok(tree);
        } catch (Exception e) {
            logger.error("获取菜单树失败", e);
            return ResponseBo.error("获取菜单树失败！");
        }
    }

    @RequestMapping("menu/getUserMenu")
    @ResponseBody
    public ResponseBo getUserMenu(String userName) {
        try {
            Tree<Menu> tree = this.menuService.getUserMenu(userName);
            return ResponseBo.ok(tree);
        } catch (Exception e) {
            logger.error("获取用户菜单失败", e);
            return ResponseBo.error("获取用户菜单失败！");
        }
    }

    @RequestMapping("menu/list")
    @RequiresPermissions("system:menu:list")
    @ResponseBody
    public List<Menu> menuList(Menu menu) {
        try {
            return this.menuService.all(menu);
        } catch (Exception e) {
            logger.error("获取菜单集合失败", e);
            return new ArrayList<>();
        }
    }

    @RequestMapping("menu/excel")
    @ResponseBody
    public ResponseBo menuExcel(Menu menu) {
        try {
            List<Menu> list = this.menuService.all(menu);
            return FileUtil.createExcelByPOIKit("菜单表", list, Menu.class);
        } catch (Exception e) {
            logger.error("带出菜单列表Excel失败", e);
            return ResponseBo.error("导出Excel失败，请联系网站管理员！");
        }
    }

    @RequestMapping("menu/csv")
    @ResponseBody
    public ResponseBo menuCsv(Menu menu) {
        try {
            List<Menu> list = this.menuService.all(menu);
            return FileUtil.createCsv("菜单表", list, Menu.class);
        } catch (Exception e) {
            logger.error("导出菜单列表Csv失败", e);
            return ResponseBo.error("导出Csv失败，请联系网站管理员！");
        }
    }

    @RequestMapping("menu/checkMenuName")
    @ResponseBody
    public boolean checkMenuName(String menuName, String MenuType, String oldMenuName) {
        if (StringUtils.isNotBlank(oldMenuName) && menuName.equalsIgnoreCase(oldMenuName)) {
            return true;
        }
        Menu result = this.menuService.getByNameAndType(menuName, MenuType);
        return result == null;
    }

    @RequiresPermissions("system:menu:add")
    @GetMapping("menu/add")
    public String addMenuView(ModelMap mmap) {
        return PATH_PREFIX + "add";
    }


    @Log("新增菜单/按钮")
    @RequiresPermissions("system:menu:add")
    @RequestMapping("menu/add")
    @ResponseBody
    public ResponseBo addMenu(Menu menu) {
        MenuTypeEnum menuTypeEnum = MenuTypeEnum.getByCode(menu.getMenuType());
        if (menuTypeEnum == null){
            return ResponseBo.error("菜单类型[" + menuTypeEnum.getRemark() + "]有误！");
        }
        this.menuService.insert(menu);
        return ResponseBo.ok("新增" + menuTypeEnum.getRemark() + "成功！");

    }

    @Log("删除菜单")
    @RequiresPermissions("system:menu:delete")
    @RequestMapping("menu/delete")
    @ResponseBody
    public ResponseBo deleteMenus(String ids) {
        try {
            this.menuService.delete(ids);
            return ResponseBo.ok("删除成功！");
        } catch (Exception e) {
            logger.error("获取菜单失败", e);
            return ResponseBo.error("删除失败，请联系网站管理员！");
        }
    }

    @RequiresPermissions("system:menu:update")
    @GetMapping("menu/update/{id}")
    public String updateMenuView(@PathVariable("id") String id, ModelMap mmap) {
        Menu menu = menuService.get(id);
        mmap.put("menu", menu);
        return PATH_PREFIX + "update";
    }

    @Log("修改菜单/按钮")
    @RequiresPermissions("system:menu:update")
    @RequestMapping("menu/update")
    @ResponseBody
    public ResponseBo updateMenu(Menu menu) {
        MenuTypeEnum menuTypeEnum = MenuTypeEnum.getByCode(menu.getMenuType());
        if (menuTypeEnum == null){
            return ResponseBo.error("菜单类型[" + menuTypeEnum.getRemark() + "]有误！");
        }
        if (StringUtils.isBlank(menu.getParentId())){
            menu.setParentId(null);
        }
        this.menuService.updateExcludeNull(menu);
        return ResponseBo.ok("修改" + menuTypeEnum.getRemark() + "成功！");

    }


    @Log("获取系统所有URL")
    @GetMapping("menu/urlList")
    @ResponseBody
    public List<Map<String, String>> getAllUrl() {
        return this.menuService.getAllUrl("1");
    }

}
