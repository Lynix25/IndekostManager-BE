package com.indekos.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class AccountChangePasswordRequest extends AuditableRequest {
    @NotBlank(message = "password is required")
    private String oldPassword;

    @NotBlank(message = "newpassword is required")
    private String newPassword;

    @NotBlank(message = "verifiedPassword is required")
    private String reTypeNewPassword;

}
