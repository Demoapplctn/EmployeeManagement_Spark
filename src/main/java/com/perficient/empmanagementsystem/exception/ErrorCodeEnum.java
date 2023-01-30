package com.perficient.empmanagementsystem.exception;


import java.util.Collections;
import java.util.List;
import static com.perficient.empmanagementsystem.common.CignaConstantUtils.PROVIDE_CORRECT_EMAIL;
import static com.perficient.empmanagementsystem.common.CignaConstantUtils.PROVIDE_CORRECT_EMAIL_PASSWORD;
public enum ErrorCodeEnum {
    INPUT_PARAM_ERROR("",-1001),
	INPUT_EMAIL_ERROR(PROVIDE_CORRECT_EMAIL,-1002),
	INPUT_EMAIL_AND_PASSWORD_ERROR(PROVIDE_CORRECT_EMAIL_PASSWORD,-1003);
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
