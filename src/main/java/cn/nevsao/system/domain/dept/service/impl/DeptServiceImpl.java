package cn.nevsao.system.domain.dept.service.impl;

import cn.nevsao.common.mvc.vo.Tree;
import cn.nevsao.common.mvc.mapper.MyMapper;
import cn.nevsao.common.mvc.service.impl.ExtraService;
import cn.nevsao.common.util.TreeUtils;
import cn.nevsao.system.domain.dept.entity.Dept;
import cn.nevsao.system.domain.dept.mapper.DeptMapper;
import cn.nevsao.system.domain.dept.service.DeptService;
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
			tree.setId(dept.getId().toString());
			tree.setParentId(dept.getParentId().toString());
			tree.setText(dept.getName());
			trees.add(tree);
		});
		return TreeUtils.build(trees);
	}

	@Override
	public List<Dept> findAllDepts(Dept dept) {
//		try {
//			Example example = new Example(Dept.class);
//			if (StringUtils.isNotBlank(dept.getName())) {
//				example.createCriteria().andCondition("name=", dept.getName());
//			}
//			example.setOrderByClause("id");
//			return this.findByExample(example);
//		} catch (Exception e) {
//			log.error("获取部门列表失败", e);
//			return new ArrayList<>();
//		}

		Example example = new Example(Dept.class);
		if (StringUtils.isNotBlank(dept.getName())) {
			example.createCriteria().andCondition("name=", dept.getName());
		}
		example.setOrderByClause("id");
		return this.findByExample(example);

	}

	@Override
	public Dept getByName(String deptName) {
		Example example = new Example(Dept.class);
		example.createCriteria().andCondition("lower(name) =", deptName.toLowerCase());
		List<Dept> list = this.findByExample(example);
		return list.isEmpty() ? null : list.get(0);
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
