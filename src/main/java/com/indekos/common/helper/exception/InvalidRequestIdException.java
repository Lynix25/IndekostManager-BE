package com.indekos.common.helper.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class InvalidRequestIdException extends RuntimeException{
    private final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public InvalidRequestIdException (String message){
        super(message);
    }
}
