package cn.nevsao.system.dept.entity;

import cn.nevsao.common.annotation.ExportConfig;
import cn.nevsao.common.mvc.entity.BaseEntityExtra;
import cn.nevsao.system.user.entity.User;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "sys_dept")
@Data
public class Dept extends BaseEntityExtra {

    private static final long serialVersionUID = -7790334862410409053L;

    @Column(name = "parent_id")
    private String parentId;

    @Column(name = "name")
    @ExportConfig(value = "部门名称")
    private String name;

    @Column(name = "order_seq")
    private Long orderSeq;

    @Column(name = "principal_id")
    private String principalId;

    @Transient
    private User manager;


}