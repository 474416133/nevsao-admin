package cn.nevsao.job.mapper;

import cn.nevsao.common.mvc.mapper.MyMapper;
import cn.nevsao.job.entity.Job;

import java.util.List;

public interface JobMapper extends MyMapper<Job> {
	
	List<Job> queryList();
}