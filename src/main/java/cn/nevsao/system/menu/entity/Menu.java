package cn.nevsao.system.menu.entity;

import cn.nevsao.common.annotation.ExportConfig;
import cn.nevsao.common.mvc.entity.BaseEntityExtra;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "sys_menu")
@Data
public class Menu extends BaseEntityExtra {
    private static final long serialVersionUID = 7187628714679791771L;

    @Column(name = "parent_id")
    private String parentId;

    @Column(name = "name")
    @ExportConfig(value = "名称")
    private String name;

    @Column(name = "url")
    @ExportConfig(value = "地址")
    private String url;

    @Column(name = "perms")
    @ExportConfig(value = "权限标识")
    private String perms;

    @Column(name = "icon")
    @ExportConfig(value = "图标")
    private String icon;

    @Column(name = "menu_type")
    @ExportConfig(value = "类型", convert = "s:0=菜单,1=按钮")
    private String menuType;

    @Column(name = "order_seq")
    private Long orderSeq;

    @Transient
    private boolean checked = false;

    @Transient
    private Menu parent;


}