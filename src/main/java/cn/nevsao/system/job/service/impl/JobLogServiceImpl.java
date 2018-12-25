package cn.nevsao.system.job.service.impl;

import cn.nevsao.common.mvc.mapper.MyMapper;
import cn.nevsao.common.mvc.service.impl.BaseService;
import cn.nevsao.system.job.entity.JobLog;
import cn.nevsao.system.job.mapper.JobLogMapper;
import cn.nevsao.system.job.service.JobLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("JobLogService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class JobLogServiceImpl extends BaseService<JobLog> implements JobLogService {

    @Autowired
    private JobLogMapper jobLogMapper;

    @Override
    public MyMapper getMapper() {
        return jobLogMapper;
    }

    @Override
    public List<JobLog> findAllJobLogs(JobLog jobLog) {
        try {
            Example example = new Example(JobLog.class);
            Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(jobLog.getBeanName())) {
                criteria.andCondition("bean_name=", jobLog.getBeanName());
            }
            if (StringUtils.isNotBlank(jobLog.getMethodName())) {
                criteria.andCondition("method_name=", jobLog.getMethodName());
            }
            if (StringUtils.isNotBlank(jobLog.getRetStatus())) {
                criteria.andCondition("status=", Long.valueOf(jobLog.getRetStatus()));
            }
            example.setOrderByClause("id desc");
            return this.findByExample(example);
        } catch (Exception e) {
            log.error("获取调度日志信息失败", e);
            return new ArrayList<>();
        }
    }


}
