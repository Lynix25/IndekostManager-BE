package com.indekos.common.helper.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class MethodNotAllowedException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public MethodNotAllowedException(String message, Throwable cause) {
		super(message, cause);
	}

	public MethodNotAllowedException(String message) {
		super(message);
	}

	public MethodNotAllowedException(Throwable cause) {
		super(cause);
	}
}
