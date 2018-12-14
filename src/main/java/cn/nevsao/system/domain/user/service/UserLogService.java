package cn.nevsao.system.domain.user.service;

import cn.nevsao.common.mvc.service.IService;
import cn.nevsao.system.domain.user.entity.UserLog;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface UserLogService extends IService<UserLog> {
	
	List<UserLog> findAllLogs(UserLog log);

	@Async
	void saveLog(ProceedingJoinPoint point, UserLog log) throws JsonProcessingException;
}
