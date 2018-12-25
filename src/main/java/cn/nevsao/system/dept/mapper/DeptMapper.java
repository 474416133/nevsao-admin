package cn.nevsao.system.dept.mapper;

import cn.nevsao.common.mvc.mapper.MyMapper;
import cn.nevsao.system.dept.entity.Dept;

import java.util.List;

public interface DeptMapper extends MyMapper<Dept> {

    // 删除父节点，子节点变成顶级节点（根据实际业务调整）
    void changeToTop(List<String> deptIds);
}