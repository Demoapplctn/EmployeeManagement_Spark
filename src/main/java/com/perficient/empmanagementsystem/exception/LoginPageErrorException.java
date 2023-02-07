package com.perficient.empmanagementsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class LoginPageErrorException extends CignaBaseException{
	private static final long serialVersionUID = 4148710948750185420L;
	public LoginPageErrorException(String message) {
		super(message);

	}
}
