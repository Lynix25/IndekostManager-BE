package com.indekos.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class AccountRegisterRequest extends AuditableRequest{
    
	@NotBlank(message = "username is required")
    private String username;
    
	@NotBlank(message = "password is required")
    private String password;

    private UserRegisterRequest user;
}
