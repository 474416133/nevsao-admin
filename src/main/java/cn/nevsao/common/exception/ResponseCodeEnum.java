package cn.nevsao.common.exception;

public enum ResponseCodeEnum {
    OK(0, "OK"),
    WARN(1000, "WARN"),
    ERROR(1001, "ERROR"),
    SERVER_DATA_NOT_EXIT(1002, "数据不存在"),
    SERVER_DATA_HAD_EXIST(1003, "数据已存在"),
    CLIENT_PARAMS_ERROR(10000, "参数错误"),
    FILE_DOWNLOAD_ERROR(1004, "文件下载发生出错"),
    LIMIT_ACCESS_ERROR(9000, "访问过于频繁");


    private int code;
    private String msg;

    ResponseCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode(){
        return code;
    }

    public String getMsg(){
        return msg;
    }
}
