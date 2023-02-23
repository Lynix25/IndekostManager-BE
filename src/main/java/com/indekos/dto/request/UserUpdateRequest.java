package com.indekos.dto.request;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class UserUpdateRequest extends AuditableRequest{
    private String alias;

    private String email;

    private String phone;

    private String job;
}
