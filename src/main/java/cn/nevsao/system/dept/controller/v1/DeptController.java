package cn.nevsao.system.dept.controller.v1;

import cn.nevsao.common.annotation.Log;
import cn.nevsao.common.mvc.vo.ResponseBo;
import cn.nevsao.common.mvc.vo.Tree;
import cn.nevsao.common.util.FileUtil;
import cn.nevsao.system.dept.entity.Dept;
import cn.nevsao.system.dept.service.DeptService;
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

import java.util.List;

@Controller
@RequestMapping(value = "system/")
public class DeptController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final static String PATH_PREFIX = "system/dept/";
    @Autowired
    private DeptService deptService;

    @Log("获取部门信息")
    @RequestMapping("dept")
    @RequiresPermissions("system:dept:list")
    public String index() {
        return PATH_PREFIX + "dept";
    }

    @GetMapping("dept/tree/view")
    public String deptTreeView( ModelMap mmap) {
        return PATH_PREFIX + "tree";
    }

    @RequestMapping("dept/tree")
    @ResponseBody
    public ResponseBo getDeptTree() {

        Tree<Dept> tree = this.deptService.getDeptTree();
        return ResponseBo.ok(tree);
    }

    @RequestMapping("dept/getDept")
    @ResponseBody
    public ResponseBo getDept(String deptId) {

        Dept dept = this.deptService.get(deptId);
        return ResponseBo.ok(dept);
    }

    @RequestMapping("dept/list")
    @RequiresPermissions("system:dept:list")
    @ResponseBody
    public List<Dept> deptList(Dept dept) {

        return this.deptService.findAllDepts(dept);
    }

    @RequestMapping("dept/excel")
    @ResponseBody
    public ResponseBo deptExcel(Dept dept) {


        List<Dept> list = this.deptService.findAllDepts(dept);
        return FileUtil.createExcelByPOIKit("部门表", list, Dept.class);
    }

    @RequestMapping("dept/csv")
    @ResponseBody
    public ResponseBo deptCsv(Dept dept) {

        List<Dept> list = this.deptService.findAllDepts(dept);
        return FileUtil.createCsv("部门表", list, Dept.class);
    }

    @RequestMapping("dept/checkDeptName")
    @ResponseBody
    public boolean checkDeptName(String name, String oldName) {
        if (StringUtils.isNotBlank(oldName) && name.equalsIgnoreCase(oldName)) {
            return true;
        }
        Dept result = this.deptService.getByName(name);
        return result == null;
    }


    @RequiresPermissions("system:dept:add")
    @GetMapping("dept/add")
    public String addDeptView( ModelMap mmap) {
        return PATH_PREFIX + "add";
    }

    @Log("新增部门")
    @RequiresPermissions("system:dept:add")
    @RequestMapping("dept/add")
    @ResponseBody
    public ResponseBo addDept(Dept dept) {

        this.deptService.insert(dept);
        return ResponseBo.ok("新增部门成功！");
    }

    @Log("删除部门")
    @RequiresPermissions("system:dept:delete")
    @RequestMapping("dept/delete")
    @ResponseBody
    public ResponseBo deleteDepts(String ids) {
        this.deptService.delete(ids);
        return ResponseBo.ok("删除部门成功！");
    }

    @RequiresPermissions("system:dept:update")
    @GetMapping("dept/update/{id}")
    public String updateDeptView(@PathVariable("id") String id, ModelMap mmap) {
        Dept dept = deptService.getWithParent(id);
        mmap.put("dept", dept);
        return PATH_PREFIX + "update";
    }

    @Log("修改部门 ")
    @RequiresPermissions("system:dept:update")
    @RequestMapping("dept/update")
    @ResponseBody
    public ResponseBo updateDept(Dept dept) {


        this.deptService.update(dept);
        return ResponseBo.ok("修改部门成功！");
    }



}
