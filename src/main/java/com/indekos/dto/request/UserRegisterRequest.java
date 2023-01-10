package com.indekos.dto.request;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class UserRegisterRequest {
	
	private String name;
	private String alias;
	private String email;
	private String phone;
	private String job;
	private String gender;
	private String description;
	private String roleId;
    private String roomId;
    private String accountId;
    
    private String user;
}
