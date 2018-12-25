package cn.nevsao.system.user.controller.v1;

import cn.nevsao.common.mvc.vo.QueryRequest;
import cn.nevsao.common.mvc.vo.ResponseBo;
import cn.nevsao.common.mvc.controller.BaseController;
import cn.nevsao.common.util.FileUtil;
import cn.nevsao.system.user.entity.UserLog;
import cn.nevsao.system.user.service.UserLogService;
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


        List<UserLog> list = this.userLogService.findAllLogs(log);
        return FileUtil.createExcelByPOIKit("系统日志表", list, UserLog.class);
    }

    @RequestMapping("log/csv")
    @ResponseBody
    public ResponseBo logCsv(UserLog log) {

        List<UserLog> list = this.userLogService.findAllLogs(log);
        return FileUtil.createCsv("系统日志表", list, UserLog.class);
    }

    @RequiresPermissions("log:delete")
    @RequestMapping("log/delete")
    @ResponseBody
    public ResponseBo deleteLogss(String ids) {

        this.userLogService.delete(ids);
        return ResponseBo.ok("删除日志成功！");
    }
}
