package com.perficient.empmanagementsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DuplicateEntryException extends CignaBaseException{
    private static final long serialVersionUID = 590803464812880611L;
    public DuplicateEntryException(String message){
        super(message);
    }

}
