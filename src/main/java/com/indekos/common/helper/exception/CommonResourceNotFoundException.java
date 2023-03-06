package com.indekos.common.helper.exception;

public class CommonResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public int code;
	public Object data;
	
	public CommonResourceNotFoundException(String message) {
		super(message);
		this.code = 4102;
	}

	public CommonResourceNotFoundException(String message, Object data) {
		super(message);
		this.code = 4102;
		this.data = data;
	}

}
