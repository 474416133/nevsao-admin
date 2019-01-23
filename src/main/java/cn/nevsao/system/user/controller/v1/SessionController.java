package cn.nevsao.system.user.controller.v1;

import cn.nevsao.common.annotation.Log;
import cn.nevsao.common.mvc.vo.ResponseBo;
import cn.nevsao.system.user.entity.UserOnline;
import cn.nevsao.system.user.service.SessionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value = "system/")
public class SessionController {

    @Autowired
    SessionService sessionService;
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Log("获取在线用户信息")
    @RequestMapping("session")
    @RequiresPermissions("system:session:list")
    public String online() {
        return "monitor/online/online";
    }

    @ResponseBody
    @RequestMapping("session/list")
    @RequiresPermissions("system:session:list")
    public Map<String, Object> list() {
        List<UserOnline> list = sessionService.list();
        Map<String, Object> rspData = new HashMap<>();
        rspData.put("rows", list);
        rspData.put("total", list.size());
        return rspData;
    }

    @ResponseBody
    @RequiresPermissions("system:user:kickout")
    @RequestMapping("session/forceLogout")
    public String forceLogout(String[] ids) {
        Arrays.asList(ids).forEach(id ->{
            sessionService.forceLogout(id);
        });
        return "操作成功！";
    }
}
