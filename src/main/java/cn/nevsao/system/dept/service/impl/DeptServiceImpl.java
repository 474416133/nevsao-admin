package cn.nevsao.system.dept.service.impl;

import cn.nevsao.common.mvc.vo.Tree;
import cn.nevsao.common.mvc.mapper.MyMapper;
import cn.nevsao.common.mvc.service.impl.ExtraService;
import cn.nevsao.common.util.TreeUtils;
import cn.nevsao.system.dept.entity.Dept;
import cn.nevsao.system.dept.mapper.DeptMapper;
import cn.nevsao.system.dept.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service("deptService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
@Slf4j
public class DeptServiceImpl extends ExtraService<Dept> implements DeptService {

	@Autowired
	private DeptMapper deptMapper;

	@Override
	public MyMapper getMapper(){
		return deptMapper;
	}


	@Override
	public List<Dept> findAllDepts(Dept dept) {

		Example example = new Example(Dept.class);
		if (StringUtils.isNotBlank(dept.getName())) {
			example.createCriteria().andLike("name", "%"+dept.getName()+"%");
		}
		example.setOrderByClause("id");
		return this.findByExample(example);

	}

	@Override
	public Dept getWithParent(String id) {
		Dept dept = getWithPrincipal(id);
		if (dept != null && StringUtils.isNotBlank(dept.getParentId())){
			Dept parent = get(dept.getParentId());
			dept.setParent(parent);
		}
		return dept;
	}

	@Override
	public List<Dept> findWithPrincipal(Dept dept) {
		return deptMapper.findWithPrincipal(dept);
	}

	@Override
	public Dept getWithPrincipal(String id) {
		return deptMapper.getWithPrincipal(id);
	}

	@Override
	@Transactional
	public int delete(List<String> deptIds) {
		int ret = this.delete(deptIds, Dept.class);
		this.deptMapper.changeToTop(deptIds);
		return ret;
	}


}
