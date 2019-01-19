package cn.nevsao.common.mvc.service;

import cn.nevsao.common.mvc.entity.BaseEntity;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public interface IService<T extends BaseEntity> {

	List<T> all();

	T get(String key);

	int insert(T entity);

	int delete(String key);

	int delete(List<String> list, Class<T> clazz);

	int delete(List<String> list, String property, Class<T> clazz);

	int update(T entity);

	int updateExcludeNull(T entity);

	List<T> findByExample(Example example);

	//名字校验
	void checkName(Class<? extends BaseEntity> clazz, String name, String id);

	//根据名字查询
	List<T> findByName(Class<? extends BaseEntity> clazz, String name);

	/**
	 * 根据id列表，删除
	 * @param ids
	 * @return
	 */
	int delete(List<String> ids);
}