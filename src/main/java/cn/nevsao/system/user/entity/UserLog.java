package cn.nevsao.system.user.entity;

import cn.nevsao.common.annotation.ExportConfig;
import cn.nevsao.common.mvc.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

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

    @Column(name = "op_desc")
    @ExportConfig(value = "描述")
    private String operation;

    @Column(name = "op_time")
    @ExportConfig(value = "耗时（毫秒）")
    private Long Time;

    @Column(name = "op_method")
    @ExportConfig(value = "操作方法")
    private String method;

    @Column(name = "op_params")
    @ExportConfig(value = "参数")
    private String params;

    @Column(name = "op_ip")
    @ExportConfig(value = "IP地址")
    private int ip;

    @Column(name = "op_location")
    @ExportConfig(value = "地点")
    private String location;

    /**
     * 用于搜索条件中的时间字段
     */
    @Transient
    private String timeField;


}