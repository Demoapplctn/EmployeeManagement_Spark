package com.perficient.empmanagementsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;

@ControllerAdvice
public class HandledCustomException {

    @ExceptionHandler
    public ResponseEntity<ErrorDetails> handledCustomException(CignaBaseException ex){
        ErrorDetails response = getCustomException(ex);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    private ErrorDetails getCustomException(CignaBaseException ex) {
        ErrorDetails response=null;
        if(ex instanceof DuplicateEntryException){
            response =getErrorDetails(ex.getMessage(),ErrorCodeEnum.EMP_ID_OR_PASSWORD_DUPLICATE.getCode());
        }else if(ex instanceof EmployeeNotFoundException){
             response = getErrorDetails(ex.getMessage(), ErrorCodeEnum.INPUT_EMPID_ERROR.getCode());
        }else if(ex instanceof LoginPageErrorException){
            response = getErrorDetails(ex.getMessage(), ErrorCodeEnum.INPUT_EMAIL_AND_PASSWORD_ERROR.getCode());
        }else if(ex instanceof  InCorrectEmailException){
            response = getErrorDetails(ex.getMessage(), ErrorCodeEnum.INPUT_EMAIL_ERROR.getCode());
        }
        else if(ex instanceof EmptyFileException) {
            response = getErrorDetails(ex.getMessage(), ErrorCodeEnum.FILE_CONTENT_IS_EMPTY.getCode());
        }
        return response;
    }

    private ErrorDetails getErrorDetails(String message, int errorCode) {
        return ErrorDetails.builder()
                .errorCode(errorCode)
                .errorMessage(Collections.singletonList(message))
                .build();
    }
}
