package com.indekos.common.helper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.indekos.dto.response.LoginResponse;
import com.indekos.dto.response.Response;
import com.indekos.dto.response.TokenSessionResponse;
import com.indekos.model.User;

public class GlobalAcceptions {
    public static ResponseEntity loginAllowed(User user, String message){
        TokenSessionResponse token = new TokenSessionResponse("", 0);

        LoginResponse.DataResponseDto data = new LoginResponse.DataResponseDto<>();
        data.setStatus("Authenticated");
        data.setUser(user);
        data.setToken(token);

        LoginResponse response = new LoginResponse<>(message,data,null);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    public static ResponseEntity linkedUser(){
        Response response = new Response();


        return new ResponseEntity(response, HttpStatus.OK);
    }

    public static ResponseEntity registerUser(User user, String message){
        Response response = new Response<>(message, user);

        return new ResponseEntity(response, HttpStatus.OK);
    }
}
