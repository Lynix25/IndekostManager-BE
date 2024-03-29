package com.indekos.dto.request;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class Request {
    @NotEmpty(message = "username is required")
    private String username;

    @NotEmpty(message = "password is required")
    private String password;
}
