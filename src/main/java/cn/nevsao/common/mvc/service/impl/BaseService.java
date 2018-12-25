package cn.nevsao.common.mvc.service.impl;

import cn.nevsao.common.mvc.entity.BaseEntity;
import cn.nevsao.common.mvc.mapper.MyMapper;
import cn.nevsao.common.mvc.service.IService;
import cn.nevsao.common.util.PKUtil;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public  class BaseService<T extends BaseEntity> implements IService<T> {

	protected MyMapper<T> getMapper() {
		throw new NotImplementedException();
	}

	@Override
	public List<T> all() {
		return getMapper().selectAll();
	}

	@Override
	public T get(String key) {
		return getMapper().selectByPrimaryKey(key);
	}

	@Override
	@Transactional
	public int insert(T entity) {
		entity.setId(PKUtil.newId());
		entity.setCreateTime(new Date());
		return getMapper().insert(entity);
	}

	@Override
	@Transactional
	public int delete(String key) {
		return getMapper().deleteByPrimaryKey(key);
	}

	@Override
	@Transactional
	public int delete(List<String> list, Class<T> clazz) {
		return delete(list, "id", clazz);
	}

	@Override
	public int delete(List<String> list, String property, Class<T> clazz) {
		Example example = new Example(clazz);
		example.createCriteria().andIn(property, list);
		return getMapper().deleteByExample(example);
	}

	@Override
	@Transactional
	public int update(T entity) {
		return getMapper().updateByPrimaryKey(entity);
	}

	@Override
	@Transactional
	public int updateExcludeNull(T entity) {
		return getMapper().updateByPrimaryKeySelective(entity);
	}

	@Override
	public List<T> findByExample(Example example) {
		return getMapper().selectByExample(example);
	}
}
