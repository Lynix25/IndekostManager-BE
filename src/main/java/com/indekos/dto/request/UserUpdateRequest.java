package com.indekos.dto.request;

import lombok.Getter;

@Getter
public class UserUpdateRequest extends AuditableRequest{
    
	private String alias;
    private String email;
    private String phone;
    private String job;
}
