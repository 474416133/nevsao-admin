package cn.nevsao.system.domain.dept.service;

import cn.nevsao.common.domain.Tree;
import cn.nevsao.common.mvc.service.IService;
import cn.nevsao.system.domain.dept.entity.Dept;

import java.util.List;

public interface DeptService extends IService<Dept> {

    Tree<Dept> getDeptTree();

    List<Dept> findAllDepts(Dept dept);

    Dept getByName(String deptName);

}
