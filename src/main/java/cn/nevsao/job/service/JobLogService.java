package cn.nevsao.job.service;

import cn.nevsao.common.mvc.service.IService;
import cn.nevsao.job.domain.JobLog;

import java.util.List;

public interface JobLogService extends IService<JobLog> {

	List<JobLog> findAllJobLogs(JobLog jobLog);

	void saveJobLog(JobLog log);
	
	void deleteBatch(String jobLogIds);
}
