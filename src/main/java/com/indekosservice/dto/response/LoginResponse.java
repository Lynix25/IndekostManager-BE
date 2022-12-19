package com.indekosservice.dto.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.log.Log;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@NoArgsConstructor
public class LoginResponse<T, U> extends Response {
    private U errors;

    public LoginResponse(String message, T data, U errors){
        super(message, data);
        this.errors = errors;
    }

    @Data
    @AllArgsConstructor @NoArgsConstructor
    public static class DataResponseDto <V>{
        private String status;
        private V user;
        private TokenSessionResponse token;
    }
}
