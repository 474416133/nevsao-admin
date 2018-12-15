package cn.nevsao.job.service;

import cn.nevsao.common.mvc.service.IService;
import cn.nevsao.job.entity.JobLog;

import java.util.List;

public interface JobLogService extends IService<JobLog> {

	List<JobLog> findAllJobLogs(JobLog jobLog);

}
