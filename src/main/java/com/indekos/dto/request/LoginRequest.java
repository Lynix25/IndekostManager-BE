package com.indekos.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class LoginRequest{
    @NotEmpty(message = "username is required")
    private String username;

    @NotEmpty(message = "password is required")
    private String password;
}
