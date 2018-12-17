package cn.nevsao.system.domain.user.controller.v1;

import cn.nevsao.common.mvc.vo.QueryRequest;
import cn.nevsao.common.mvc.vo.ResponseBo;
import cn.nevsao.common.mvc.controller.BaseController;
import cn.nevsao.common.util.FileUtil;
import cn.nevsao.system.domain.user.entity.UserLog;
import cn.nevsao.system.domain.user.service.UserLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class UserLogController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserLogService userLogService;

    @RequestMapping("log")
    @RequiresPermissions("log:list")
    public String index() {
        return "system/log/log";
    }

    @RequestMapping("log/list")
    @ResponseBody
    public Map<String, Object> logList(QueryRequest request, UserLog log) {
        return super.selectByPageNumSize(request, () -> this.userLogService.findAllLogs(log));
    }

    @RequestMapping("log/excel")
    @ResponseBody
    public ResponseBo logExcel(UserLog log) {
//        try {
//            List<UserLog> list = this.userLogService.findAllLogs(log);
//            return FileUtil.createExcelByPOIKit("系统日志表", list, UserLog.class);
//        } catch (Exception e) {
//            logger.error("导出系统日志Excel失败", e);
//            return ResponseBo.error("导出Excel失败，请联系网站管理员！");
//        }

        List<UserLog> list = this.userLogService.findAllLogs(log);
        return FileUtil.createExcelByPOIKit("系统日志表", list, UserLog.class);
    }

    @RequestMapping("log/csv")
    @ResponseBody
    public ResponseBo logCsv(UserLog log) {
//        try {
//            List<UserLog> list = this.userLogService.findAllLogs(log);
//            return FileUtil.createCsv("系统日志表", list, UserLog.class);
//        } catch (Exception e) {
//            logger.error("导出系统日志Csv失败", e);
//            return ResponseBo.error("导出Csv失败，请联系网站管理员！");
//        }
        List<UserLog> list = this.userLogService.findAllLogs(log);
        return FileUtil.createCsv("系统日志表", list, UserLog.class);
    }

    @RequiresPermissions("log:delete")
    @RequestMapping("log/delete")
    @ResponseBody
    public ResponseBo deleteLogss(String ids) {
//        try {
//            this.userLogService.delete(ids);
//            return ResponseBo.ok("删除日志成功！");
//        } catch (Exception e) {
//            logger.error("删除日志失败", e);
//            return ResponseBo.error("删除日志失败，请联系网站管理员！");
//        }
        this.userLogService.delete(ids);
        return ResponseBo.ok("删除日志成功！");
    }
}
