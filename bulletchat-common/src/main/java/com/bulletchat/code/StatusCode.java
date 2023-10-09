package com.bulletchat.code;

public enum StatusCode {
    SUCCESS("10000","操作成功"),
    DECRYPT_FAILED("1","解密失败"),
    NO_VALID_USER("2","该用户不存在"),
    WRONG_PASSWORD("3","密码错误"),
    EXPIRED_TOKEN("4","Token过期"),
    INVALID_TOKEN("5","非法Token"),
    EXCEPTION_TOKEN("6","Token异常"),
    UPDATE_FAILED("7","更新失败"),
    OTHER_EXCEPTION("50000","其他错误");
    private String code;
    private String info;

    StatusCode(String code, String info){
        this.code = code;
        this.info = info;
    }

    public String getCode(){ return code;}
    public String getInfo(){return info;}
}
