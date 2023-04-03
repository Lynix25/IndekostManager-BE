package com.indekos.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class AccountForgotPasswordRequest {
	
    @NotBlank(message = "username is required")
    private String username;

    @NotBlank(message = "new password is required")
    private String newPassword;

    @NotBlank(message = "verified password is required")
    private String reTypeNewPassword;
}
