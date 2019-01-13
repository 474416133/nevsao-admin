package cn.nevsao.system.dept.service;

import cn.nevsao.common.mvc.vo.Tree;
import cn.nevsao.common.mvc.service.IService;
import cn.nevsao.system.dept.entity.Dept;

import java.util.List;

public interface DeptService extends IService<Dept> {

    Tree<Dept> getDeptTree();

    List<Dept> findAllDepts(Dept dept);

    Dept getByName(String deptName);

    Dept getWithParent(String id);

}
