package cn.nevsao.common.aspect;

import cn.nevsao.common.config.FebsProperties;
import cn.nevsao.common.util.HttpContextUtils;
import cn.nevsao.common.util.IPUtils;
import cn.nevsao.system.domain.user.entity.UserLog;
import cn.nevsao.system.domain.user.entity.User;
import cn.nevsao.system.domain.user.service.UserLogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * AOP 记录用户操作日志
 *
 * @author MrBird
 * @link https://nevsao.cn/Spring-Boot-AOP%20log.html
 */
@Aspect
@Component
public class LogAspect {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FebsProperties febsProperties;

    @Autowired
    private UserLogService userLogService;


    @Pointcut("@annotation(cn.nevsao.common.annotation.Log)")
    public void pointcut() {
        // do nothing
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws JsonProcessingException {
        Object result = null;
        long beginTime = System.currentTimeMillis();
        try {
            // 执行方法
            result = point.proceed();
        } catch (Throwable e) {
            log.error(e.getMessage());
        }
        // 获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        // 设置IP地址
        String ip = IPUtils.getIpAddr(request);
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        if (febsProperties.isOpenAopLog()) {
            // 保存日志
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            UserLog log = new UserLog();
            log.setUsername(user.getUsername());
            //log.setIp(ip);
            log.setPeriodTime(time);
            userLogService.saveLog(point, log);
        }
        return result;
    }
}
