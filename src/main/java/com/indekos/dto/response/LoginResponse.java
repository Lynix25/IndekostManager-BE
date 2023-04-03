package com.indekos.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Data
public class LoginResponse<T, U> extends Response<T> {
    private U errors;

    public LoginResponse(String message, T data, U errors){
        super(message, data);
        this.errors = errors;
    }

    @Data
    @AllArgsConstructor @NoArgsConstructor
    public static class DataResponseDto <V>{
        private String status;
        private Long loginTime;
        private V user;
        private TokenSessionResponse token;
    }
}
