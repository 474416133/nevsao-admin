package cn.nevsao.system.domain.user.controller.v1;

import cn.nevsao.common.annotation.Log;
import cn.nevsao.common.enu.AccountActiveEnum;
import cn.nevsao.common.enu.GenderEnum;
import cn.nevsao.common.exception.BaseException;
import cn.nevsao.common.exception.ResponseCodeEnum;
import cn.nevsao.common.mvc.controller.BaseController;
import cn.nevsao.common.mvc.vo.QueryRequest;
import cn.nevsao.common.mvc.vo.ResponseBo;
import cn.nevsao.common.util.FileUtil;
import cn.nevsao.common.util.MD5Utils;
import cn.nevsao.system.domain.user.entity.User;
import cn.nevsao.system.domain.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class UserController extends BaseController {

    private static final String ON = "on";
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserService userService;

    @RequestMapping("user")
    @RequiresPermissions("user:list")
    public String index(Model model) {
        User user = super.getCurrentUser();
        model.addAttribute("user", user);
        return "system/user/user";
    }

    @RequestMapping("user/checkUserName")
    @ResponseBody
    public boolean checkUserName(String username, String oldusername) {
        if (StringUtils.isNotBlank(oldusername) && username.equalsIgnoreCase(oldusername)) {
            return true;
        }
        User result = this.userService.getByName(username);
        return result == null;
    }

    @RequestMapping("user/getUser")
    @ResponseBody
    public ResponseBo getUser(String userId) {
        try {
            User user = this.userService.get(userId);
            return ResponseBo.ok(user);
        } catch (Exception e) {
            log.error("获取用户失败", e);
            return ResponseBo.error("获取用户失败，请联系网站管理员！");
        }
    }

    @Log("获取用户信息")
    @RequestMapping("user/list")
    @RequiresPermissions("user:list")
    @ResponseBody
    public Map<String, Object> userList(QueryRequest request, User user) {
        return super.selectByPageNumSize(request, () -> this.userService.findUserWithDept(user, request));
    }

    @RequestMapping("user/excel")
    @ResponseBody
    public ResponseBo userExcel(User user) {

        List<User> list = this.userService.findUserWithDept(user, null);
        return FileUtil.createExcelByPOIKit("用户表", list, User.class);
    }

    @RequestMapping("user/csv")
    @ResponseBody
    public ResponseBo userCsv(User user) {

        List<User> list = this.userService.findUserWithDept(user, null);
        return FileUtil.createCsv("用户表", list, User.class);
    }

    @RequestMapping("user/regist")
    @ResponseBody
    public ResponseBo regist(User user) {


        User result = this.userService.getByName(user.getUsername());
        if (result != null) {
            return ResponseBo.warn("该用户名已被使用！");
        }
        this.userService.registUser(user);
        return ResponseBo.ok();
    }

    @Log("更换主题")
    @RequestMapping("user/theme")
    @ResponseBody
    public ResponseBo updateTheme(User user) {

        this.userService.updateTheme(user.getThemeUsing(), user.getUsername());
        return ResponseBo.ok();
    }

    @Log("新增用户")
    @RequiresPermissions("user:add")
    @RequestMapping("user/add")
    @ResponseBody
    public ResponseBo addUser(User user, String[] roles) {

        AccountActiveEnum activeEnum = AccountActiveEnum.getByCode(user.getIsActive());
        if (activeEnum == null) {
            throw new BaseException(ResponseCodeEnum.CLIENT_PARAMS_ERROR);
        }
        this.userService.insert(user, roles);
        return ResponseBo.ok("新增用户成功！");
    }

    @Log("修改用户")
    @RequiresPermissions("user:update")
    @RequestMapping("user/update")
    @ResponseBody
    public ResponseBo updateUser(User user, String[] rolesSelect) {

        AccountActiveEnum activeEnum = AccountActiveEnum.getByCode(user.getIsActive());
        if (activeEnum == null) {
            throw new BaseException(ResponseCodeEnum.CLIENT_PARAMS_ERROR);
        }
        this.userService.update(user, rolesSelect);
        return ResponseBo.ok("修改用户成功！");
    }

    @Log("删除用户")
    @RequiresPermissions("user:delete")
    @RequestMapping("user/delete")
    @ResponseBody
    public ResponseBo deleteUsers(String ids) {

        this.userService.delete(ids);
        return ResponseBo.ok("删除用户成功！");
    }

    @RequestMapping("user/checkPassword")
    @ResponseBody
    public boolean checkPassword(String password) {
        User user = getCurrentUser();
        String encrypt = MD5Utils.encrypt(user.getUsername().toLowerCase(), password);
        return user.getPassword().equals(encrypt);
    }

    @RequestMapping("user/updatePassword")
    @ResponseBody
    public ResponseBo updatePassword(String newPassword) {

        this.userService.resetPassword(newPassword);
        return ResponseBo.ok("更改密码成功！");
    }

    @RequestMapping("user/profile")
    public String profileIndex(Model model) {
        User user = super.getCurrentUser();
        user = this.userService.getUserProfile(user);
        user.setGenderRemark(GenderEnum.getByCode(user.getGender()).getRemark());
        model.addAttribute("user", user);
        return "system/user/profile";
    }

    @RequestMapping("user/getUserProfile")
    @ResponseBody
    public ResponseBo getUserProfile(String userId) {

        User user = new User();
        user.setId(userId);
        return ResponseBo.ok(this.userService.getUserProfile(user));
    }

    @RequestMapping("user/updateUserProfile")
    @ResponseBody
    public ResponseBo updateUserProfile(User user) {

        this.userService.updateUserProfile(user);
        return ResponseBo.ok("更新个人信息成功！");
    }

    @RequestMapping("user/changeAvatar")
    @ResponseBody
    public ResponseBo changeAvatar(String imgName) {
        String[] img = imgName.split("/");
        String realImgName = img[img.length - 1];
        User user = getCurrentUser();
        user.setAvatar(realImgName);
        this.userService.updateExcludeNull(user);
        return ResponseBo.ok("更新头像成功！");
    }
}
