package com.perficient.empmanagementsystem.exception;

import java.util.Collections;
import java.util.List;

public enum ErrorCodeEnum {
    INPUT_PARAM_ERROR("",-1001);
    private final List<String> messages;

    private final Integer code;

    ErrorCodeEnum(String msg, Integer code) {
        this.messages = Collections.singletonList(msg);
        this.code=code;
    }
    public List<String> getMessages(){
        return messages;
    }
    public Integer getCode(){
        return code;
    }
}
