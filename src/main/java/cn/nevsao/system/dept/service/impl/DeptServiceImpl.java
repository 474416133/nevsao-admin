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
	public Tree<Dept> getDeptTree() {
		List<Tree<Dept>> trees = new ArrayList<>();
		List<Dept> depts = this.findAllDepts(new Dept());
		depts.forEach(dept -> {
			Tree<Dept> tree = new Tree<>();
			tree.setId(dept.getId());
			tree.setParentId(dept.getParentId());
			tree.setText(dept.getName());
			trees.add(tree);
		});
		return TreeUtils.build(trees);
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
	public Dept getByName(String deptName) {
		List<Dept> list = this.findByName(Dept.class, deptName);
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public Dept getWithParent(String id) {
		Dept dept = get(id);
		if (dept != null && StringUtils.isNotBlank(dept.getParentId())){
			Dept parent = get(dept.getParentId());
			dept.setParent(parent);
		}
		return dept;
	}

	@Override
	@Transactional
	public int delete(String deptIds) {
		List<String> list = Arrays.asList(deptIds.split(","));
		int ret = this.delete(list, Dept.class);
		this.deptMapper.changeToTop(list);
		return ret;
	}


}
