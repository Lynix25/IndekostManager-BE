package com.indekos.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ChangePassword {
    @NotEmpty(message = "account id is required")
    private String id;

    @NotEmpty(message = "password is required")
    private String oldPassword;

    @NotEmpty(message = "newpassword is required")
    private String newPassword;

    @NotEmpty(message = "verifiedPassword is required")
    private String reTypeNewPassword;

}
