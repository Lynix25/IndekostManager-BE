package com.indekos.common.helper.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class InvalidRequestException extends RuntimeException{
    private final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
    private final List<String> errors;

    public InvalidRequestException (String message, List<String> errors){
        super(message);
        this.errors = errors;
    }
}
