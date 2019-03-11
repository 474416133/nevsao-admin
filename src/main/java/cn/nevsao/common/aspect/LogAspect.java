package cn.nevsao.common.aspect;

import cn.nevsao.common.annotation.Log;
import cn.nevsao.common.config.FebsProperties;
import cn.nevsao.common.util.AddressUtils;
import cn.nevsao.common.util.HttpContextUtils;
import cn.nevsao.common.util.IPUtils;
import cn.nevsao.common.util.SpringContextUtils;
import cn.nevsao.system.user.entity.UserLog;
import cn.nevsao.system.user.entity.User;
import cn.nevsao.system.user.service.UserLogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.Executor;

/**
 * AOP 记录用户操作日志
 *
 * @author MrBird
 * @link https://nevsao.cn/Spring-Boot-AOP%20log.html
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

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
        UserLog userLog = new UserLog();
        try {
            // 执行方法
            result = point.proceed();
            userLog.setRetCode(0);
        } catch (Throwable e) {
            String msg = e.getMessage();
            log.error(e.getMessage());
            userLog.setRetCode(1);
            userLog.setRetMsg(msg);
            if(userLog.getRetMsg().length() > 2000){
                userLog.setRetMsg(userLog.getRetMsg().substring(0, 2000));
            }
        }
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        // 获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        // 设置IP地址
        String ip = IPUtils.getIpAddr(request);
        userLog.setPeriodTime(time);
        userLog.setUserIp(ip);
        userLog.setUserLocation(AddressUtils.getCityInfo(ip));
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        userLog.setUsername(user.getUsername());
        userLog.setUserId(user.getId());

        userLogService.saveLog(point, userLog);
        return result;
    }

}
