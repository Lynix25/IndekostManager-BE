package com.indekos.common.helper.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DataAlreadyExistException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public DataAlreadyExistException() {
		super("Data already exist!");
	}

	public DataAlreadyExistException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataAlreadyExistException(String message) {
		super(message);
	}

	public DataAlreadyExistException(Throwable cause) {
		super(cause);
	}

}
