package com.indekos.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class RoomCreateRequest extends AuditableRequest {
	private String name;
	private String description;
	private Integer quota;
	private Integer floor;
	private String allotment;
	private List<RoomFacilityCreateRequest> facilities;
	private List<RoomDetailCreateRequest> details;
}
