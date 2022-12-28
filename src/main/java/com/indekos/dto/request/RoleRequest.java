package com.indekos.dto.request;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class RoleRequest {
	
	private String name;
	private String description;
	private String user;
}
