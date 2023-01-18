package com.indekos.dto.request;

import lombok.Getter;

@Getter
public class AnnouncementRequest extends AuditableRequest {
	
	private String title;
	private String description;
	private String period;
}