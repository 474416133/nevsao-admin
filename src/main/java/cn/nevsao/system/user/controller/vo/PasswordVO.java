
package cn.nevsao.system.user.controller.vo;

import cn.nevsao.system.user.entity.User;
import lombok.Data;

/**
 *
 * @Project nevsao-admin
 * @Package cn.nevsao.system.user.controller.vo
 * @ClassName PasswordVO
 * @Author sven
 * @Date 2019年02月19日
 * @Version 1.0
 */

@Data
public class PasswordVO extends User {
    /**
     * 旧密码
     */
    private String oldPassword;
    /**
     * 确认密码
     */
    private String confirm;
}
