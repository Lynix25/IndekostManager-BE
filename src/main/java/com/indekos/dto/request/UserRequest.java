package com.indekos.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
public class UserRequest extends AuditableRequest{
    @NotBlank(message = "user name is required")
    private String name;

    @NotNull(message = "user alias is required")
    private String alias;

    @NotNull(message = "user description is required")
    private String email;

    @NotBlank(message = "user phone is required")
    private String phone;

    @NotNull(message = "user job is required")
    private String job;

    @NotBlank(message = "user gender is required")
    private String gender;

    @NotNull(message = "user description is required")
    private String description;

    @NotBlank(message = "user role id is required")
    private String roleId;

    @NotEmpty(message = "user room id can't be empty")
    private String roomId;

    @NotEmpty(message = "user account id can't be empty")
    private String accountId;
}
