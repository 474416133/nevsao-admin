package cn.nevsao.system.role.controller.v1;

import cn.nevsao.common.annotation.Log;
import cn.nevsao.common.mvc.controller.BaseController;
import cn.nevsao.common.mvc.vo.QueryRequest;
import cn.nevsao.common.mvc.vo.ResponseBo;
import cn.nevsao.common.util.FileUtil;
import cn.nevsao.system.role.entity.Role;
import cn.nevsao.system.role.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "system/")
public class RoleController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RoleService roleService;

    @Log("获取角色信息")
    @RequestMapping("role")
    @RequiresPermissions("system:role:list")
    public String index() {
        return "system/role/role";
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

    @RequestMapping("role/getRole")
    @ResponseBody
    public ResponseBo getRole(String roleId) {

        Role role = this.roleService.getRoleWithMenus(roleId);
        return ResponseBo.ok(role);
    }

    @RequestMapping("role/checkRoleName")
    @ResponseBody
    public boolean checkRoleName(String roleName, String oldRoleName) {
        if (StringUtils.isNotBlank(oldRoleName) && roleName.equalsIgnoreCase(oldRoleName)) {
            return true;
        }
        Role result = this.roleService.getByName(roleName);
        return result == null;
    }

    @Log("新增角色")
    @RequiresPermissions("system:role:add")
    @RequestMapping("role/add")
    @ResponseBody
    public ResponseBo addRole(Role role, String[] menuId) {
        this.roleService.insert(role, menuId);
        return ResponseBo.ok("新增角色成功！");
    }

    @Log("删除角色")
    @RequiresPermissions("system:role:delete")
    @RequestMapping("role/delete")
    @ResponseBody
    public ResponseBo deleteRoles(String ids) {

        this.roleService.delete(ids);
        return ResponseBo.ok("删除角色成功！");
    }

    @Log("修改角色")
    @RequiresPermissions("system:role:update")
    @RequestMapping("role/update")
    @ResponseBody
    public ResponseBo updateRole(Role role, String[] menuId) {

        this.roleService.update(role, menuId);
        return ResponseBo.ok("修改角色成功！");
    }
}
