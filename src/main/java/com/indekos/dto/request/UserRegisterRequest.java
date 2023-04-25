package com.indekos.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = false)
@Data
public class UserRegisterRequest extends AuditableRequest{
	
    @NotBlank(message = "user name is required")
    private String name;

    private String alias;

    private String email;

    @NotBlank(message = "user phone is required")
    private String phone;

    @NotNull(message = "user job is required")
    private String job;

    @NotBlank(message = "user gender is required")
    private String gender;
    
    private boolean married;

    private String description;

//    @NotBlank(message = "user role id is required")
    private String roleId;

    private String roomId;

    private MultipartFile identityCardImage;
}