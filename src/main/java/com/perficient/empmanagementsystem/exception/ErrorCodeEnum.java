package com.perficient.empmanagementsystem.exception;


import java.util.Collections;
import java.util.List;

import static com.perficient.empmanagementsystem.common.CignaConstantUtils.*;

public enum ErrorCodeEnum {
    INPUT_PARAM_ERROR("",1001),
	INPUT_EMAIL_ERROR(PROVIDE_CORRECT_EMAIL,1002),
	INPUT_EMAIL_AND_PASSWORD_ERROR(PROVIDE_CORRECT_EMAIL_PASSWORD,1003),
	INPUT_EMPID_ERROR(PROVIDE_CORRECT_ID,1004),
	EMP_ID_OR_PASSWORD_DUPLICATE(EMPLOYEE_EMAIL_EXIST,1005),
    FILE_CONTENT_IS_EMPTY(FILE_EMPTY,1006);


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
