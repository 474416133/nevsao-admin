package cn.nevsao.system.user.controller.v1;

import cn.nevsao.common.annotation.Log;
import cn.nevsao.common.enu.BitEnum;
import cn.nevsao.common.exception.BaseException;
import cn.nevsao.common.exception.ResponseCodeEnum;
import cn.nevsao.common.mvc.controller.BaseController;
import cn.nevsao.common.mvc.vo.QueryRequest;
import cn.nevsao.common.mvc.vo.ResponseBo;
import cn.nevsao.common.util.FileUtil;
import cn.nevsao.common.util.MD5Utils;
import cn.nevsao.system.dict.entity.Dict;
import cn.nevsao.system.dict.service.DictService;
import cn.nevsao.system.role.entity.Role;
import cn.nevsao.system.role.service.RoleService;
import cn.nevsao.system.user.entity.User;
import cn.nevsao.system.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "system/")
@Slf4j
public class UserController extends BaseController {

    private static final String ON = "on";
    private static final String PATH_PREFIX = "system/user/";

    @Autowired
    private UserService userService;

    @Autowired
    private DictService dictService;

    @Autowired
    private RoleService roleService;

    @RequestMapping("user")
    @RequiresPermissions("system:user:list")
    public String index(Model model) {
        User user = super.getCurrentUser();
        model.addAttribute("user", user);
        return PATH_PREFIX + "user";
    }

    @RequestMapping("user/checkUserName")
    @ResponseBody
    public void checkUserName(String username) {
        userService.checkUsername(username);
    }

    @RequestMapping("user/checkEmail")
    @ResponseBody
    public void checkEmail(String email, String id) {
        userService.checkEmail(email, id);
    }

    @RequestMapping("user/checkMobile")
    @ResponseBody
    public void checkMobile(String mobile, String id) {
        userService.checkMobile(mobile, id);
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
    @RequiresPermissions("system:user:list")
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

    @RequiresPermissions("system:user:add")
    @GetMapping("user/add")
    public String addUserView(ModelMap mmap) {
        Dict paramObj = new Dict();
        paramObj.setColumnName("title");
        paramObj.setTableName("sys_user");
        List<Dict> titles = dictService.all(paramObj, null);
        mmap.put("titles", titles);
        List<Role> roles = roleService.all();
        mmap.put("roles", roles);
        return PATH_PREFIX + "add";
    }

    @Log("新增用户")
    @RequiresPermissions("system:user:add")
    @RequestMapping("user/add")
    @ResponseBody
    public String addUser(User user, String[] roleIds) {
        BitEnum activeEnum = BitEnum.getByCode(user.getIsActive());
        if (activeEnum == null) {
            throw new BaseException(ResponseCodeEnum.CLIENT_PARAMS_ERROR);
        }
        this.userService.insert(user, roleIds);
        return "新增用户成功！";
    }

    @RequiresPermissions("system:user:update")
    @RequestMapping("user/update/{id}")
    public String updateUserView(@PathVariable("id") String id, ModelMap mmap) {

        User user = userService.getWithDept(id);
        mmap.put("user", user);

        Dict paramObj = new Dict();
        paramObj.setColumnName("title");
        paramObj.setTableName("sys_user");
        List<Dict> titles = dictService.all(paramObj, null);
        mmap.put("titles", titles);
        if (StringUtils.isNotBlank(user.getTitle())){
            Map<String, Dict> dictMap = new HashMap<>();
            List<String> userTiles = Arrays.asList(user.getTitle().split(","));
            for (Dict dict: titles){
                dictMap.put(dict.getDictKey(), dict);
            }

            for (String title: userTiles){
                Dict dict = dictMap.get(title);
                if (dict != null){
                    dict.setFlag("selected");
                }
            }
        }

        List<Role> roles = roleService.allWithFlagByUser(user.getId());
        mmap.put("roles", roles);

        return  PATH_PREFIX + "update";
    }

    @Log("修改用户")
    @RequiresPermissions("system:user:update")
    @RequestMapping("user/update")
    @ResponseBody
    public String updateUser(User user, String[] roleIds) {

        BitEnum activeEnum = BitEnum.getByCode(user.getIsActive());
        if (activeEnum == null) {
            throw new BaseException(ResponseCodeEnum.CLIENT_PARAMS_ERROR);
        }
        this.userService.update(user, roleIds);
        return "修改用户成功！";
    }

    @Log("删除用户")
    @RequiresPermissions("system:user:delete")
    @RequestMapping("user/delete")
    @ResponseBody
    public String deleteUsers(String[] ids) {

        this.userService.delete(Arrays.asList(ids));
        return "删除用户成功！";
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
    public String updatePassword(String newPassword) {

        this.userService.resetPassword(newPassword);
        return "更改密码成功！";
    }

    @RequestMapping("user/profile")
    public String profileIndex(Model model) {
        User user = super.getCurrentUser();
        user = this.userService.getUserProfile(user);
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
    public String updateUserProfile(User user) {

        this.userService.updateUserProfile(user);
        return "更新个人信息成功！";
    }

    @RequestMapping("user/changeAvatar")
    @ResponseBody
    public String changeAvatar(String imgName) {
        String[] img = imgName.split("/");
        String realImgName = img[img.length - 1];
        User user = getCurrentUser();
        user.setAvatar(realImgName);
        this.userService.updateExcludeNull(user);
        return "更新头像成功！";
    }
}
