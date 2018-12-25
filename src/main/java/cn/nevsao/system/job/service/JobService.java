package cn.nevsao.system.job.service;

import cn.nevsao.common.mvc.service.IService;
import cn.nevsao.system.job.entity.Job;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@CacheConfig(cacheNames = "JobService")
public interface JobService extends IService<Job> {

    List<Job> findAllJobs(Job job);

    int updateBatch(String jobIds, Integer status);

    void run(String jobIds);

    void pause(String jobIds);

    void resume(String jobIds);

     @Cacheable(key = "#p0")
    List<Job> getSysCronClazz(Job job);
}
