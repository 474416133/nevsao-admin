package cn.nevsao.common.enu;

import java.util.HashMap;
import java.util.Map;

public enum MenuTypeEnum {
    MENU("0", "菜单"),
    BUTTON("1", "按钮");

    private String code;
    private String remark;

    private static Map<String, MenuTypeEnum> menuTypeMap = new HashMap();
    static {
        menuTypeMap.put(MENU.getCode(), MENU);
        menuTypeMap.put(BUTTON.getCode(), BUTTON);
    }

    MenuTypeEnum(String code, String remark) {
        this.code = code;
        this.remark = remark;
    }
    public String getCode(){
        return code;
    }

    public String getRemark(){
        return remark;
    }

    public static  MenuTypeEnum getByCode(String code){
        return menuTypeMap.get(code);
    }
}
