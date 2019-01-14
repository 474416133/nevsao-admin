package cn.nevsao.system.role.entity;

import cn.nevsao.common.annotation.ExportConfig;
import cn.nevsao.common.mvc.entity.BaseEntityExtra;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "sys_role")
@Data
public class Role extends BaseEntityExtra {
    private static final long serialVersionUID = -1714476694755654924L;


    @Column(name = "name")
    @ExportConfig(value = "角色")
    private String name;

    @Transient
    private String flag;


}