package com.perficient.empmanagementsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class InCorrectEmailException extends CignaBaseException{

	private static final long serialVersionUID = -2094887032397095716L;

	public InCorrectEmailException(String message) {
		super(message);
		
	}

}
