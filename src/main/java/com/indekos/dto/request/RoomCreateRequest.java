package com.indekos.dto.request;

import lombok.Getter;

@Getter
public class RoomCreateRequest extends AuditableRequest {
	
	private String name;
	private Integer floor;
	private Integer quota;
	private String allotment;
	private String description;
}
