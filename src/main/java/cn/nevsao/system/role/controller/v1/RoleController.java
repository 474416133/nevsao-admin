package cn.nevsao.system.role.controller.v1;

import cn.nevsao.common.annotation.Log;
import cn.nevsao.common.mvc.controller.BaseController;
import cn.nevsao.common.mvc.vo.QueryRequest;
import cn.nevsao.common.mvc.vo.ResponseBo;
import cn.nevsao.common.mvc.vo.Tree;
import cn.nevsao.common.util.FileUtil;
import cn.nevsao.system.menu.entity.Menu;
import cn.nevsao.system.role.entity.Role;
import cn.nevsao.system.role.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "system/")
public class RoleController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private static  final String PATH_PREFIX = "system/role/";
    @Autowired
    private RoleService roleService;

    @Log("获取角色信息")
    @RequestMapping("role")
    @RequiresPermissions("system:role:list")
    public String index() {
        return PATH_PREFIX + "role";
    }

    @RequestMapping("role/list")
    @RequiresPermissions("system:role:list")
    @ResponseBody
    public Map<String, Object> roleList(QueryRequest request, Role role) {
        return super.selectByPageNumSize(request, () -> this.roleService.all(role));
    }

    @RequestMapping("role/excel")
    @ResponseBody
    public ResponseBo roleExcel(Role role) {

        List<Role> list = this.roleService.all(role);
        return FileUtil.createExcelByPOIKit("角色表", list, Role.class);
    }

    @RequestMapping("role/csv")
    @ResponseBody
    public ResponseBo roleCsv(Role role) {
        List<Role> list = this.roleService.all(role);
        return FileUtil.createCsv("角色表", list, Role.class);
    }


    @RequestMapping("role/checkRoleName")
    @ResponseBody
    public void checkRoleName(String id, String name) {
        roleService.checkName(Role.class, name, id);
    }

    @RequiresPermissions("system:role:add")
    @GetMapping("role/add")
    public String addRoleView(ModelMap mmap) {
        return PATH_PREFIX + "add";
    }

    @Log("新增角色")
    @RequiresPermissions("system:role:add")
    @RequestMapping("role/add")
    @ResponseBody
    public String addRole(Role role, String[] menuIds) {
        this.roleService.insert(role, menuIds);
        return "新增角色成功！";
    }

    @Log("删除角色")
    @RequiresPermissions("system:role:delete")
    @RequestMapping("role/delete")
    @ResponseBody
    public String deleteRoles(String[] ids) {
        this.roleService.delete(Arrays.asList(ids));
        return "删除角色成功！";
    }

    @RequiresPermissions("system:role:update")
    @GetMapping("role/update/{id}")
    public String updateRoleView(@PathVariable("id") String id, ModelMap mmap ) {
        Role role = roleService.get(id);
        mmap.put("role", role);
        return PATH_PREFIX + "update";
    }

    @Log("修改角色")
    @RequiresPermissions("system:role:update")
    @RequestMapping("role/update")
    @ResponseBody
    public String updateRole(Role role, String[] menuIds) {
        this.roleService.update(role, menuIds);
        return "修改角色成功！";
    }

    @RequiresPermissions("system:role:list")
    @RequestMapping("role/menu/list")
    @ResponseBody
    public List<Menu> fineMenuList(Role role) {
        return roleService.menuList(role.getId());
    }
}
