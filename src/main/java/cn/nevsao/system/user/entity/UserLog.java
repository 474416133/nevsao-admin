package cn.nevsao.system.user.entity;

import cn.nevsao.common.annotation.ExportConfig;
import cn.nevsao.common.mvc.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Table(name = "sys_user_log")
@Data
public class UserLog extends BaseEntity {

    private static final long serialVersionUID = -8878596941954995444L;

    @Column(name = "user_id")
    @ExportConfig(value = "操作用户")
    private String userId;

    @Column(name = "username")
    @ExportConfig(value = "操作用户")
    private String username;

    @Column(name = "method_remark")
    @ExportConfig(value = "描述")
    private String methodRemark;

    @Column(name = "period_time")
    @ExportConfig(value = "耗时（毫秒）")
    private Long periodTime;

    @Column(name = "method_name")
    @ExportConfig(value = "操作方法")
    private String methodName;

    @Column(name = "method_params")
    @ExportConfig(value = "参数")
    private String methodParams;

    @Column(name = "user_ip")
    @ExportConfig(value = "IP地址")
    private String userIp;

    @Column(name = "user_location")
    @ExportConfig(value = "地点")
    private String userLocation;

    @Column(name = "ret_code")
    @ExportConfig(value = "运行状态，0/1=正常/异常")
    private Integer retCode;

    @Column(name = "ret_msg")
    @ExportConfig(value = "错误信息")
    private String retMsg;

    /**
     * 用于搜索条件中的时间字段
     */
    @Transient
    private Date startTime;

    @Transient
    private Date endTime;


}