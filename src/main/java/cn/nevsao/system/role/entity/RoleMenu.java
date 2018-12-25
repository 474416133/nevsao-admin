package cn.nevsao.system.role.entity;

import cn.nevsao.common.mvc.entity.BaseEntityExtra;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "sys_role_menu")
@Data
public class RoleMenu extends BaseEntityExtra {

    private static final long serialVersionUID = -7573904024872252113L;

    @Column(name = "role_id")
    private String roleId;

    @Column(name = "menu_id")
    private String menuId;

}