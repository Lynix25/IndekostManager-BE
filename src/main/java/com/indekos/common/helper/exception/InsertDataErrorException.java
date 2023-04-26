package com.indekos.common.helper.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InsertDataErrorException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public InsertDataErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	public InsertDataErrorException(String message) {
		super(message);
	}

	public InsertDataErrorException(Throwable cause) {
		super(cause);
	}
}
