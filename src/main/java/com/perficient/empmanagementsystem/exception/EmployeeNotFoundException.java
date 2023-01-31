package com.perficient.empmanagementsystem.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EmployeeNotFoundException extends CignaBaseException{
    private static final long serialVersionUID = 1L;
    public EmployeeNotFoundException(){

    }
    public EmployeeNotFoundException(String message){
        super(message);
    }
}
