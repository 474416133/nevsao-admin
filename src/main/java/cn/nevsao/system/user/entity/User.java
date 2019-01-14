package cn.nevsao.system.user.entity;

import cn.nevsao.common.annotation.Dict;
import cn.nevsao.common.annotation.ExportConfig;
import cn.nevsao.common.enu.BitEnum;
import cn.nevsao.common.mvc.entity.BaseEntityExtra;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Table(name = "sys_user")
@Data
public class User extends BaseEntityExtra {
    private static final long serialVersionUID = -4852732617765810959L;

    /**
     * 后台主题
     */
    public static final String DEFAULT_THEME = "green";
    public static final String DEFAULT_AVATAR = "default.jpg";


    @Column(name = "username")
    @ExportConfig(value = "用户名")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "dept_id")
    private String deptId;

    @Transient
    @ExportConfig(value = "部门")
    private String deptName;

    @Column(name = "email")
    @ExportConfig(value = "邮箱")
    private String email;

    @Column(name = "mobile")
    @ExportConfig(value = "手机")
    private String mobile;

    @Column(name = "is_active")
    @ExportConfig(value = "状态", convert = "s:0=锁定,1=有效")
    private Integer isActive = BitEnum.ACTIVE.getCode();

    @Column(name = "last_login_time")
    private Date lastLoginTime;

    @Column(name = "gender")
    @ExportConfig(value = "性别", convert = "s:0=男,1=女,2=保密")
    private Character gender;

    @Column(name = "theme_using")
    private String themeUsing;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "remark")
    private String remark;

    @Column(name = "last_login_ip")
    private Long lastLoginIp;

    @Dict
    @Column(name = "title")
    private String title;


    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", deptId=" + deptId +
                ", deptName='" + deptName + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", isActive='" + isActive + '\'' +
                ", createTime=" + getCreateTime() +
                ", modifyTime=" + getModifyTime() +
                ", lastLoginTime=" + lastLoginTime +
                ", gender='" + gender + '\'' +
                ", themeUsing='" + themeUsing + '\'' +
                ", avatar='" + avatar + '\'' +
                ", remark='" + remark + '\'' +
                ""   +
                '}';
    }

    /**
     * shiro-redis v3.1.0 必须要有getAuthCacheKey()或者getId()方法
     * # Principal id field name. The field which you can get unique id to identify this principal.
     * # For example, if you use UserInfo as Principal class, the id field maybe userId, userName, email, etc.
     * # Remember to add getter to this id field. For example, getUserId(), getUserName(), getEmail(), etc.
     * # Default value is authCacheKey or id, that means your principal object has a method called "getAuthCacheKey()" or "getId()"
     *
     * @return userId as Principal id field name
     */
    public String getAuthCacheKey() {
        return getId();
    }
}