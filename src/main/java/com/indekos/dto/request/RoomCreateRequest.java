package com.indekos.dto.request;

import lombok.Getter;

import javax.persistence.Column;

@Getter
public class RoomCreateRequest extends AuditableRequest {
	private String name;
	private String description;
	private Integer quota;
	private Integer floor;
	private Integer price;
}
