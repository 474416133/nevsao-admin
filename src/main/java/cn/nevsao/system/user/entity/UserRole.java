package cn.nevsao.system.user.entity;

import cn.nevsao.common.mvc.entity.BaseEntityExtra;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "sys_user_role")
@Data
public class UserRole extends BaseEntityExtra {

    private static final long serialVersionUID = -3166012934498268403L;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "role_id")
    private String roleId;

}