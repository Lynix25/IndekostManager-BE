package com.indekos.common.helper.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ErrorsExceptions extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
    public ErrorsExceptions (String message){
        super(message);
    }
}