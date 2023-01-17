package com.indekos.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Getter
@Data
public class UserRegisterRequest{
    @NotEmpty(message = "user name is required")
    private String name;

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

    @JsonProperty(value = "role_id")
    private String roleId;

    @JsonProperty(value = "room_id")
    private String roomId;

    @JsonProperty(value = "account_id")
    private String accountId;
    
    private String user;

    @NotEmpty(message = "user who create is required")
//    @JsonProperty(value = "created_by")
    private String createdBy;

//    @NotEmpty(message = "user who edit is required")
//    @JsonProperty(value = "last_modified_by")
//    private String lastModifiedBy;

}
