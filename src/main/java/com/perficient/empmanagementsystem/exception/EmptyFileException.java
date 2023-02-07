package com.perficient.empmanagementsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EmptyFileException extends CignaBaseException{

    private static final long serialVersionUID = -5610022623968738314L;

    public EmptyFileException(String message) {
        super(message);

    }
}
