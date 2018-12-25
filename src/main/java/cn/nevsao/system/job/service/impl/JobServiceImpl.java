package cn.nevsao.system.job.service.impl;

import cn.nevsao.common.annotation.CronTag;
import cn.nevsao.common.mvc.mapper.MyMapper;
import cn.nevsao.common.mvc.service.impl.BaseService;
import cn.nevsao.system.job.entity.Job;
import cn.nevsao.system.job.mapper.JobMapper;
import cn.nevsao.system.job.service.JobService;
import cn.nevsao.system.job.util.ScheduleUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service("JobService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class JobServiceImpl extends BaseService<Job> implements JobService {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private JobMapper jobMapper;

    @Override
    public MyMapper getMapper() {
        return jobMapper;
    }

    /**
     * 项目启动时，初始化定时器
     */
    @PostConstruct
    public void init() {
        List<Job> scheduleJobList = this.jobMapper.queryList();
        // 如果不存在，则创建
        scheduleJobList.forEach(scheduleJob -> {
            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getId());
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            } else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
        });
    }


    @Override
    public List<Job> findAllJobs(Job job) {
        try {
            Example example = new Example(Job.class);
            Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(job.getBeanName())) {
                criteria.andCondition("bean_name=", job.getBeanName());
            }
            if (StringUtils.isNotBlank(job.getMethodName())) {
                criteria.andCondition("method_name=", job.getMethodName());
            }
            if (job.getStatus() != null) {
                criteria.andCondition("status=", Long.valueOf(job.getStatus()));
            }
            example.setOrderByClause("id");
            return this.findByExample(example);
        } catch (Exception e) {
            log.error("获取任务失败", e);
            return new ArrayList<>();
        }
    }


    @Override
    @Transactional
    public int insert(Job job) {

        job.setStatus(0);
        int ret = super.insert(job);
        ScheduleUtils.createScheduleJob(scheduler, job);
        return ret;
    }

    @Override
    @Transactional
    public int update(Job job) {
        ScheduleUtils.updateScheduleJob(scheduler, job);
        int ret = this.updateExcludeNull(job);
        return ret;
    }

    @Override
    @Transactional
    public int delete(String jobIds) {
        List<String> list = Arrays.asList(jobIds.split(","));
        list.forEach(jobId -> ScheduleUtils.deleteScheduleJob(scheduler, jobId));
        int ret = this.delete(list, Job.class);
        return ret;
    }

    @Override
    @Transactional
    public int updateBatch(String jobIds, Integer status) {
        List<String> list = Arrays.asList(jobIds.split(","));
        Example example = new Example(Job.class);
        example.createCriteria().andIn("id", list);
        Job job = new Job();
        job.setStatus(status);
        return this.jobMapper.updateByExampleSelective(job, example);
    }

    @Override
    @Transactional
    public void run(String jobIds) {
        String[] list = jobIds.split(",");
        Arrays.stream(list).forEach(jobId -> ScheduleUtils.run(scheduler, this.get(jobId)));
    }

    @Override
    @Transactional
    public void pause(String jobIds) {
        String[] list = jobIds.split(",");
        Arrays.stream(list).forEach(jobId -> ScheduleUtils.pauseJob(scheduler, jobId));
        this.updateBatch(jobIds, 0);
    }

    @Override
    @Transactional
    public void resume(String jobIds) {
        String[] list = jobIds.split(",");
        Arrays.stream(list).forEach(jobId -> ScheduleUtils.resumeJob(scheduler, jobId));
        this.updateBatch(jobIds, 1);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<Job> getSysCronClazz(Job job) {
        Reflections reflections = new Reflections("cn.nevsao.system.job.task");
        List<Job> jobList = new ArrayList<>();
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(CronTag.class);

        for (Class cls : annotated) {
            String clsSimpleName = cls.getSimpleName();
            Method[] methods = cls.getDeclaredMethods();
            for (Method method : methods) {
                Job job1 = new Job();
                String methodName = method.getName();
                Parameter[] methodParameters = method.getParameters();
                String params = String.format("%s(%s)", methodName, Arrays.stream(methodParameters).map(item -> item.getType().getSimpleName() + " " + item.getName()).collect(Collectors.joining(", ")));

                job1.setBeanName(StringUtils.uncapitalize(clsSimpleName));
                job1.setMethodName(methodName);
                job1.setMethodParams(params);
                jobList.add(job1);
            }
        }
        return jobList;
    }

}
