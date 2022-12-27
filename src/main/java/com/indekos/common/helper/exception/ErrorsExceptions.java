package com.indekos.common.helper.exception;

import lombok.Data;

@Data
public class ErrorsExceptions extends RuntimeException{

    public ErrorsExceptions (String message){
        super(message);
    }
}
