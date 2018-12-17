package cn.nevsao.job.controller;

import cn.nevsao.common.annotation.Log;
import cn.nevsao.common.mvc.controller.BaseController;
import cn.nevsao.common.mvc.vo.QueryRequest;
import cn.nevsao.common.mvc.vo.ResponseBo;
import cn.nevsao.common.util.FileUtil;
import cn.nevsao.job.entity.Job;
import cn.nevsao.job.service.JobService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.quartz.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class JobController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JobService jobService;

    @Log("获取定时任务信息")
    @RequestMapping("job")
    @RequiresPermissions("job:list")
    public String index() {
        return "job/job/job";
    }

    @RequestMapping("job/list")
    @RequiresPermissions("job:list")
    @ResponseBody
    public Map<String, Object> jobList(QueryRequest request, Job job) {
        return super.selectByPageNumSize(request, () -> this.jobService.findAllJobs(job));
    }

    @RequestMapping("job/checkCron")
    @ResponseBody
    public boolean checkCron(String cron) {
        try {
            return CronExpression.isValidExpression(cron);
        } catch (Exception e) {
            return false;
        }
    }

    @Log("新增任务 ")
    @RequiresPermissions("job:add")
    @RequestMapping("job/add")
    @ResponseBody
    public ResponseBo addJob(Job job) {
        try {
            this.jobService.insert(job);
            return ResponseBo.ok("新增任务成功！");
        } catch (Exception e) {
            log.error("新增任务失败", e);
            return ResponseBo.error("新增任务失败，请联系网站管理员！");
        }
    }

    @Log("删除任务")
    @RequiresPermissions("job:delete")
    @RequestMapping("job/delete")
    @ResponseBody
    public ResponseBo deleteJob(String ids) {
        try {
            this.jobService.delete(ids);
            return ResponseBo.ok("删除任务成功！");
        } catch (Exception e) {
            log.error("删除任务失败", e);
            return ResponseBo.error("删除任务失败，请联系网站管理员！");
        }
    }

    @RequestMapping("job/getJob")
    @ResponseBody
    public ResponseBo getJob(String jobId) {
        try {
            Job job = this.jobService.get(jobId);
            return ResponseBo.ok(job);
        } catch (Exception e) {
            log.error("获取任务信息失败", e);
            return ResponseBo.error("获取任务信息失败，请联系网站管理员！");
        }
    }

    @Log("修改任务 ")
    @RequiresPermissions("job:update")
    @RequestMapping("job/update")
    @ResponseBody
    public ResponseBo updateJob(Job job) {
        try {
            this.jobService.update(job);
            return ResponseBo.ok("修改任务成功！");
        } catch (Exception e) {
            log.error("修改任务失败", e);
            return ResponseBo.error("修改任务失败，请联系网站管理员！");
        }
    }

    @Log("执行任务")
    @RequiresPermissions("job:run")
    @RequestMapping("job/run")
    @ResponseBody
    public ResponseBo runJob(String jobIds) {
        try {
            this.jobService.run(jobIds);
            return ResponseBo.ok("执行任务成功！");
        } catch (Exception e) {
            log.error("执行任务失败", e);
            return ResponseBo.error("执行任务失败，请联系网站管理员！");
        }
    }

    @Log("暂停任务")
    @RequiresPermissions("job:pause")
    @RequestMapping("job/pause")
    @ResponseBody
    public ResponseBo pauseJob(String jobIds) {
        try {
            this.jobService.pause(jobIds);
            return ResponseBo.ok("暂停任务成功！");
        } catch (Exception e) {
            log.error("暂停任务失败", e);
            return ResponseBo.error("暂停任务失败，请联系网站管理员！");
        }
    }

    @Log("恢复任务")
    @RequiresPermissions("job:resume")
    @RequestMapping("job/resume")
    @ResponseBody
    public ResponseBo resumeJob(String jobIds) {
        try {
            this.jobService.resume(jobIds);
            return ResponseBo.ok("恢复任务成功！");
        } catch (Exception e) {
            log.error("恢复任务失败", e);
            return ResponseBo.error("恢复任务失败，请联系网站管理员！");
        }
    }

    @RequestMapping("job/excel")
    @ResponseBody
    public ResponseBo jobExcel(Job job) {
        try {
            List<Job> list = this.jobService.findAllJobs(job);
            return FileUtil.createExcelByPOIKit("任务表", list, Job.class);
        } catch (Exception e) {
            log.error("导出任务信息Excel失败", e);
            return ResponseBo.error("导出Excel失败，请联系网站管理员！");
        }
    }

    @RequestMapping("job/csv")
    @ResponseBody
    public ResponseBo jobCsv(Job job) {
        try {
            List<Job> list = this.jobService.findAllJobs(job);
            return FileUtil.createCsv("任务表", list, Job.class);
        } catch (Exception e) {
            log.error("导出任务信息Csv失败", e);
            return ResponseBo.error("导出Csv失败，请联系网站管理员！");
        }
    }

    /**
     * @param job 定时任务
     * @return ResponseBo
     */
    @RequestMapping("job/getSysCronClazz")
    @ResponseBody
    public ResponseBo getSysCronClazz(Job job) {
        List<Job> sysCronClazz = this.jobService.getSysCronClazz(job);
        return ResponseBo.ok(sysCronClazz);
    }
}
