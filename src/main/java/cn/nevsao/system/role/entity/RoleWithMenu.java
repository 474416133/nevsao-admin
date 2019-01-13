package cn.nevsao.system.role.entity;

import lombok.Data;

import java.util.List;

@Data
public class RoleWithMenu extends Role {

    private static final long serialVersionUID = 2013847071068967187L;

    private String menuId;

    private List<String> menuIds;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public List<String> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(List<String> menuIds) {
        this.menuIds = menuIds;
    }


}
