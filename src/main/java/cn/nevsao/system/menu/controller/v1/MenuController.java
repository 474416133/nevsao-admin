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

    @RequestMapping("menu/tree/view")
    public String getMenuTreeVew() {
        return PATH_PREFIX + "tree";
    }

    @RequestMapping("menu/list")
    @RequiresPermissions("system:menu:list")
    @ResponseBody
    public List<Menu> menuList(Menu menu) {
        return this.menuService.all(menu);
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
    public void checkMenuName(String name, String menuType, String id) {
        menuService.checkNameAndType(name, menuType, id);
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
    public String addMenu(Menu menu) {
        menuService.checkNameAndType(menu.getName(), menu.getMenuType(), null);
        MenuTypeEnum menuTypeEnum = MenuTypeEnum.getByCode(menu.getMenuType());
        if (menuTypeEnum == null){
            return "菜单类型[" + menuTypeEnum.getRemark() + "]有误！";
        }
        this.menuService.insert(menu);
        return "新增[" + menuTypeEnum.getRemark() + "]成功！";
    }

    @Log("删除菜单")
    @RequiresPermissions("system:menu:delete")
    @RequestMapping("menu/delete")
    @ResponseBody
    public String deleteMenus(String ids) {
        this.menuService.delete(ids);
        return "删除成功！";
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
    public String updateMenu(Menu menu) {
        menuService.checkNameAndType(menu.getName(), menu.getMenuType(), menu.getId());
        MenuTypeEnum menuTypeEnum = MenuTypeEnum.getByCode(menu.getMenuType());
        if (menuTypeEnum == null){
            return "菜单类型[" + menuTypeEnum.getRemark() + "]有误！";
        }
        if (StringUtils.isBlank(menu.getParentId())){
            menu.setParentId(null);
        }
        this.menuService.updateExcludeNull(menu);
        return "修改[" + menuTypeEnum.getRemark() + "]成功！";
    }

}
