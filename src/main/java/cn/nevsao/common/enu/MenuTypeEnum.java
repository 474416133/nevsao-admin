package cn.nevsao.common.enu;

public enum MenuTypeEnum {
    MENU("0", "菜单"),
    BUTTON("1", "按钮");

    private String code;
    private String remark;

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
}
