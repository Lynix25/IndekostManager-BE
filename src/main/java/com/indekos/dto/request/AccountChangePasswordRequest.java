package com.indekos.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class AccountChangePasswordRequest extends AuditableRequest {
	
    @NotBlank(message = "password is required")
    private String oldPassword;

    @NotBlank(message = "new password is required")
    private String newPassword;

    @NotBlank(message = "verified password is required")
    private String reTypeNewPassword;
}
