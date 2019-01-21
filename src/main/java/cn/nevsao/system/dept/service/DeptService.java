package cn.nevsao.system.dept.service;

import cn.nevsao.common.mvc.service.IService;
import cn.nevsao.system.dept.entity.Dept;

import java.util.List;

public interface DeptService extends IService<Dept> {
    /**
     * @param dept
     * @return
     */
    List<Dept> findAllDepts(Dept dept);

    /**
     * @param id
     * @return
     */
    Dept getWithParent(String id);


    /**
     * 列表，包括负责人信息
     * @param dept
     * @return
     */
    List<Dept> findWithPrincipal(Dept dept);

    /**
     * 单个，包括负责人信息
     * @param id
     * @return
     */
    Dept getWithPrincipal(String id);


}
