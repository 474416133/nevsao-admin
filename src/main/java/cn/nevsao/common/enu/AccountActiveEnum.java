package cn.nevsao.common.enu;

import java.util.HashMap;
import java.util.Map;

public enum AccountActiveEnum {
    ACTIVE(1, "启用"),
    DISACTIVE(0, "禁用");

    private static Map<Integer, AccountActiveEnum> codeMap = new HashMap<>();

    static {
        codeMap.put(ACTIVE.getCode(), ACTIVE);
        codeMap.put(DISACTIVE.getCode(), DISACTIVE);
    }

    private int code;
    private String remark;

    AccountActiveEnum(int code, String remark) {
        this.code = code;
        this.remark = remark;
    }

    public int getCode() {
        return code;
    }

    public String getRemark() {
        return remark;
    }

    public static AccountActiveEnum getByCode(int code) {
        return codeMap.get(code);
    }
}
