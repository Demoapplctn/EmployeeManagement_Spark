package com.perficient.empmanagementsystem.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    
    @ExceptionHandler(value = inCorrectEmailErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ResponseEntity<Object> handleException(inCorrectEmailErrorException ex) {
    	ErrorDetails response= getErrorDetail(ErrorCodeEnum.INPUT_EMAIL_ERROR.getMessages(),ErrorCodeEnum.INPUT_EMAIL_ERROR.getCode());
    	return new ResponseEntity<Object>(response,HttpStatus.NOT_FOUND);
    	
    }
    
    @ExceptionHandler(value = loginPageErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ResponseEntity<Object> handleException(loginPageErrorException ex) {
    	ErrorDetails response= getErrorDetail(ErrorCodeEnum.INPUT_EMAIL_AND_PASSWORD_ERROR.getMessages(),ErrorCodeEnum.INPUT_EMAIL_AND_PASSWORD_ERROR.getCode());
    	return new ResponseEntity<Object>(response,HttpStatus.NOT_FOUND);
    	
    }
    
    @ExceptionHandler(value = ResourceNotFoundException.class)
    private ResponseEntity<Object> exception(ResourceNotFoundException resourceNotFoundException){
    	
    	ErrorDetails result = getErrorDetail(ErrorCodeEnum.INPUT_EMPID_ERROR.getMessages(), ErrorCodeEnum.INPUT_EMPID_ERROR.getCode());
    	
    	return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }
}
