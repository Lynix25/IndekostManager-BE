package com.indekosservice.helper.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class InvalidUserCredentialException extends RuntimeException{
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
