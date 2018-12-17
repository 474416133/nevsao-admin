package cn.nevsao.system.domain.user.controller.v1;

import cn.nevsao.common.annotation.Log;
import cn.nevsao.common.mvc.vo.ResponseBo;
import cn.nevsao.system.domain.user.entity.UserOnline;
import cn.nevsao.system.domain.user.service.SessionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class SessionController {

    @Autowired
    SessionService sessionService;
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Log("获取在线用户信息")
    @RequestMapping("session")
    @RequiresPermissions("session:list")
    public String online() {
        return "system/monitor/online";
    }

    @ResponseBody
    @RequestMapping("session/list")
    @RequiresPermissions("session:list")
    public Map<String, Object> list() {
        List<UserOnline> list = sessionService.list();
        Map<String, Object> rspData = new HashMap<>();
        rspData.put("rows", list);
        rspData.put("total", list.size());
        return rspData;
    }

    @ResponseBody
    @RequiresPermissions("user:kickout")
    @RequestMapping("session/forceLogout")
    public ResponseBo forceLogout(String id) {
        try {
            sessionService.forceLogout(id);
            return ResponseBo.ok();
        } catch (Exception e) {
            log.error("踢出用户失败", e);
            return ResponseBo.error("踢出用户失败");
        }

    }
}
