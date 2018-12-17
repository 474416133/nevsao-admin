package cn.nevsao.system.domain.dict.service;

import cn.nevsao.common.mvc.vo.QueryRequest;
import cn.nevsao.common.mvc.service.IService;
import cn.nevsao.system.domain.dict.entity.Dict;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@CacheConfig(cacheNames = "DictService")
public interface DictService extends IService<Dict> {

    @Cacheable(key = "#p0.toString() + (#p1 != null ? #p1.toString() : '')")
    List<Dict> all(Dict dict, QueryRequest request);

    @Override
    @Cacheable(key = "#p0")
    Dict get(String dictId);

    @Override
    @CacheEvict(allEntries = true)
    int insert(Dict dict);

    @Override
    @CacheEvict(key = "#p0", allEntries = true)
    int delete(String dictIds);

    @Override
    @CacheEvict(key = "#p0", allEntries = true)
    int update(Dict dicdt);
}
