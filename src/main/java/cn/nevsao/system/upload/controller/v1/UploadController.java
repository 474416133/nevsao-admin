/**
 * <html>
 * <body>
 * <P> Copyright©2015-2016 Yiheni. All rights reserved. </p>
 * <p> 粤ICP备16046232号-1 </p>
 * <p> Created on 2019年02月26日</p>
 * <p> Created by sven</p>
 * </body>
 * </html>
 */
package cn.nevsao.system.upload.controller.v1;

import cn.nevsao.common.annotation.Log;
import cn.nevsao.common.mvc.controller.BaseController;
import cn.nevsao.common.util.ExcelUtils;
import cn.nevsao.system.user.entity.User;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * @Project nevsao-admin
 * @Package cn.nevsao.system.upload.controller.v1
 * @ClassName UploadController
 * @Author sven
 * @Date 2019年02月26日
 * @Version 1.0
 */

@Controller
@RequestMapping("system/upload")
public class UploadController extends BaseController {
    private final static String PATH_PREFIX = "system/upload/";

    //@Log("上传图片")
    @GetMapping("upload")
    public String index() {
        return PATH_PREFIX + "upload";
    }

    @Log("上传图片")
    @PostMapping("upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file){
        List list = null;
        if (!file.isEmpty()){
            list = ExcelUtils.parseFile(file, User.class);
        }
        int size = list == null ? 0 : list.size();
        return String.valueOf(size);
    }

}
