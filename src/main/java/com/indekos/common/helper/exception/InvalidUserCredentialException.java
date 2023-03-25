package com.indekos.common.helper.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.springframework.http.HttpStatus;

import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
public class InvalidUserCredentialException extends RuntimeException{
    private static final long serialVersionUID = 1L;
	private final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
    private List<String> errors;
    
    public InvalidUserCredentialException(String message){
        super(message);
    }
    public InvalidUserCredentialException(String message, List<String> errors){
        super(message);
        this.errors = errors;
    }
}