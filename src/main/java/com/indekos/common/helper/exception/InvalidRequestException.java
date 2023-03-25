package com.indekos.common.helper.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
public class InvalidRequestException extends RuntimeException{
    private static final long serialVersionUID = 1L;
	
    private final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
    private final List<String> errors;

    public InvalidRequestException (String message){
        super(message);
        this.errors = new ArrayList<>();
    }

    public InvalidRequestException (String message, List<String> errors){
        super(message);
        this.errors = errors;
    }
}