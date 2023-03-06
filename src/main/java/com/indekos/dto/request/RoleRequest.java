package com.indekos.dto.request;

import lombok.Data;
import lombok.Getter;

@Getter
public class RoleRequest extends AuditableRequest {
	private String name;
	private String description;
}
