package com.indekos.common.helper.exception;

public class CommonServerException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	public int code;
	
	public CommonServerException(String message) {
		super(message);
		this.code = 5000;
	}
	
}

