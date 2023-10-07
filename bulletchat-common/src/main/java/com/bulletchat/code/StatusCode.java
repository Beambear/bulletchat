package com.bulletchat.code;

public enum StatusCode {
    SUCCESS("10000","操作成功"),
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
