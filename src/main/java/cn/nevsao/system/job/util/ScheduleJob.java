package cn.nevsao.system.job.util;

import cn.nevsao.common.util.SpringContextUtils;
import cn.nevsao.system.job.entity.Job;
import cn.nevsao.system.job.entity.JobLog;
import cn.nevsao.system.job.service.JobLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 定时任务
 *
 * @author MrBird
 */

@Slf4j
public class ScheduleJob extends QuartzJobBean {
    
    private ExecutorService service = Executors.newSingleThreadExecutor();

    @Override
    protected void executeInternal(JobExecutionContext context) {
        Job scheduleJob = (Job) context.getMergedJobDataMap().get(Job.JOB_PARAM_KEY);

        // 获取spring bean
        JobLogService scheduleJobLogService = (JobLogService) SpringContextUtils.getBean("JobLogService");

        JobLog jobLog = new JobLog();
        jobLog.setJobId(scheduleJob.getId());
        jobLog.setBeanName(scheduleJob.getBeanName());
        jobLog.setMethodName(scheduleJob.getMethodName());
        jobLog.setMethodParams(scheduleJob.getMethodParams());
        jobLog.setCreateTime(new Date());

        long startTime = System.currentTimeMillis();

        try {
            // 执行任务
            log.info("任务准备执行，任务ID：{}", scheduleJob.getId());
            ScheduleRunnable task = new ScheduleRunnable(scheduleJob.getBeanName(), scheduleJob.getMethodName(),
                    scheduleJob.getMethodParams());
            Future<?> future = service.submit(task);
            future.get();
            long times = System.currentTimeMillis() - startTime;
            jobLog.setPeriodTime(times);
            // 任务状态 0：成功 1：失败
            jobLog.setRetStatus("0");

            log.info("任务执行完毕，任务ID：{} 总共耗时：{} 毫秒", scheduleJob.getId(), times);
        } catch (Exception e) {
            log.error("任务执行失败，任务ID：" + scheduleJob.getId(), e);
            long times = System.currentTimeMillis() - startTime;
            jobLog.setPeriodTime(times);
            // 任务状态 0：成功 1：失败
            jobLog.setRetStatus("1");
            jobLog.setRetRemark(StringUtils.substring(e.toString(), 0, 2000));
        } finally {
            scheduleJobLogService.insert(jobLog);
        }
    }
}
