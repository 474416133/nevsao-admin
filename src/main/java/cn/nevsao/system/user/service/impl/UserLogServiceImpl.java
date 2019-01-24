package cn.nevsao.system.user.service.impl;

import cn.nevsao.common.annotation.Log;
import cn.nevsao.common.mvc.mapper.MyMapper;
import cn.nevsao.common.mvc.service.impl.BaseService;
import cn.nevsao.common.util.AddressUtils;
import cn.nevsao.common.util.DateUtil;
import cn.nevsao.system.user.entity.UserLog;
import cn.nevsao.system.user.mapper.UserLogMapper;
import cn.nevsao.system.user.service.UserLogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.*;

@Service("logService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
@Slf4j
public class UserLogServiceImpl extends BaseService<UserLog> implements UserLogService {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserLogMapper userLogMapper;

    @Override
    protected MyMapper<UserLog> getMapper() {
        return userLogMapper;
    }

    @Override
    public List<UserLog> findAllLogs(UserLog userLog) {
        try {
            Example example = new Example(UserLog.class);
            Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(userLog.getUsername())) {
                criteria.andCondition("username=", userLog.getUsername().toLowerCase());
            }
            if (StringUtils.isNotBlank(userLog.getMethodRemark())) {
                criteria.andCondition("method_remark like", "%" + userLog.getMethodRemark() + "%");
            }
            if (userLog.getStartTime() != null && userLog.getEndTime() != null){
                if (userLog.getEndTime().before(userLog.getStartTime())){
                    return new ArrayList<>();
                }
                criteria.andCondition("create_time >=", DateUtil.getDateFormat(userLog.getStartTime(), "yyyy-MM-dd"));
                criteria.andCondition("create_time <", DateUtil.getDateFormat(userLog.getEndTime(), "yyyy-MM-dd"));
            }
            example.setOrderByClause("create_time desc");
            return this.findByExample(example);
        } catch (Exception e) {
            log.error("获取系统日志失败,{}", e);
            return new ArrayList<>();
        }
    }


    @Override
    public void saveLog(ProceedingJoinPoint joinPoint, UserLog userLog) throws JsonProcessingException {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Log logAnnotation = method.getAnnotation(Log.class);
        if (logAnnotation != null) {
            // 注解上的描述
            userLog.setMethodRemark(logAnnotation.value());
        }
        // 请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        // 请求的方法名
        String methodName = signature.getName();
        userLog.setMethodName(className + "." + methodName + "()");
        // 请求的方法参数值
        Object[] args = joinPoint.getArgs();
        // 请求的方法参数名称
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);
        if (args != null && paramNames != null) {
            StringBuilder params = new StringBuilder();
            params = handleParams(params, args, Arrays.asList(paramNames));
            if (StringUtils.isNotBlank(params) && params.length()> 2000){
                userLog.setMethodParams(params.toString().substring(0, 2000));
            }else{
                userLog.setMethodParams(params.toString());
            }
        }
        userLog.setCreateTime(new Date());
        insert(userLog);
    }

    @SuppressWarnings("unchecked")
    private StringBuilder handleParams(StringBuilder params, Object[] args, List paramNames) throws JsonProcessingException {
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof Map) {
                Set set = ((Map) args[i]).keySet();
                List list = new ArrayList();
                List paramList = new ArrayList<>();
                for (Object key : set) {
                    list.add(((Map) args[i]).get(key));
                    paramList.add(key);
                }
                return handleParams(params, list.toArray(), paramList);
            } else {
                if (args[i] instanceof Serializable) {
                    Class<?> aClass = args[i].getClass();
                    try {
                        aClass.getDeclaredMethod("toString", new Class[]{null});
                        // 如果不抛出NoSuchMethodException 异常则存在 toString 方法 ，安全的writeValueAsString ，否则 走 Object的 toString方法
                        params.append("  ").append(paramNames.get(i)).append(": ").append(objectMapper.writeValueAsString(args[i]));
                    } catch (NoSuchMethodException e) {
                        params.append("  ").append(paramNames.get(i)).append(": ").append(objectMapper.writeValueAsString(args[i].toString()));
                    }
                } else if (args[i] instanceof MultipartFile) {
                    MultipartFile file = (MultipartFile) args[i];
                    params.append("  ").append(paramNames.get(i)).append(": ").append(file.getName());
                } else {
                    params.append("  ").append(paramNames.get(i)).append(": ").append(args[i]);
                }
            }
        }
        return params;
    }
}


