package cn.nevsao.common.enu;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Set;

public enum GenderEnum {
    M('2', "女"),
    F('1', "男"),
    SECRET('0', "保密");

    private char code;
    private String remark;

    private static Map<Character, GenderEnum> codeMap;
    static {
        codeMap.put(M.getCode(), M);
        codeMap.put(F.getCode(), F);
        codeMap.put(SECRET.getCode(), SECRET);
    }


    GenderEnum(char code, String remark) {
        this.code = code;
        this.remark = remark;
    }


    public char getCode() {
        return code;
    }

    public String getRemark() {
        return remark;
    }

    public static GenderEnum getByCode(char code){
        return codeMap.get(code);
    }
}
