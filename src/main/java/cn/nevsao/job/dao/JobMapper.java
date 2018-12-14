package cn.nevsao.job.dao;

import cn.nevsao.common.mvc.mapper.MyMapper;
import cn.nevsao.job.domain.Job;

import java.util.List;

public interface JobMapper extends MyMapper<Job> {
	
	List<Job> queryList();
}