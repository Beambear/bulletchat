package com.bulletchat.exception;

import com.bulletchat.code.StatusCode;
import lombok.Getter;

@Getter
public class ConditionException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private String code;

    public ConditionException(String code, String name){
        super(name);
        this.code = code;
    }
    public ConditionException(StatusCode status){
        super(status.name());
        this.code = status.getCode();
    }

    public ConditionException(String name){
        super(name);
        code = "400";
    }

    public void setCode(String code) {
        this.code = code;
    }
}
