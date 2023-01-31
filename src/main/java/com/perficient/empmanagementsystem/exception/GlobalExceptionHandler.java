package com.perficient.empmanagementsystem.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus
            status, WebRequest request) {

        List<String> errorMsg=ex.getBindingResult().getFieldErrors()
                .stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        ErrorDetails response=getErrorDetail(errorMsg,ErrorCodeEnum.INPUT_PARAM_ERROR.getCode());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    private ErrorDetails getErrorDetail(List<String> errorMessage, int enumErrorCode){
        return ErrorDetails.builder()
                .errorCode(enumErrorCode)
                .errorMessage(errorMessage)
                .build();
    }


}
