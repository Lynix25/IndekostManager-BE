package com.indekos.common.helper;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.indekos.common.helper.exception.InvalidRequestException;
import com.indekos.common.helper.exception.InvalidUserCredentialException;
import com.indekos.dto.response.LoginResponse;
import com.indekos.dto.response.Response;

@ControllerAdvice
public class GlobalExceptions {

    @ExceptionHandler(InvalidUserCredentialException.class)
    public ResponseEntity wrongUsernameOrPassword(InvalidUserCredentialException exception){
        LoginResponse.DataResponseDto data = new LoginResponse.DataResponseDto<>();
        data.setStatus("Not Authenticated");
        data.setUser(null);
        data.setToken(null);

        LoginResponse response = new LoginResponse<>(exception.getMessage(), data, exception.getErrors());
//        response.setMessage(exception.getMessage());
//        response.setData(data);
//        response.setErrors(exception.getErrors());

        return new ResponseEntity(response, exception.getHttpStatus());
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity requestNotComplete(InvalidRequestException exception){
        Response response = new Response<>();

        response.setMessage(exception.getMessage());
        response.setData(exception.getErrors());

        return new ResponseEntity(response, exception.getHttpStatus());
    }
}
