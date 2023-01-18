package com.indekos.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UserRegisterRequest extends AuditableRequest{
    private String id;
    @NotEmpty(message = "user name is required")
    private String name;

    @NotEmpty(message = "user alias is required")
    private String alias;

    @NotEmpty(message = "user description is required")
    private String email;

    @NotEmpty(message = "user phone is required")
    private String phone;

    @NotEmpty(message = "user job is required")
    private String job;

    @NotEmpty(message = "user gender is required")
    private String gender;

    @NotNull(message = "user description is required")
    private String description;

    @NotNull(message = "user role id is required")
    private String roleId;

    @NotNull(message = "user room id is required")
    private String roomId;

    @NotNull(message = "user account id is required")
    private String accountId;

}
