package com.indekos.dto.request;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class AnnouncementRequest {
	
	private String title;
	private String description;
	private String period;
	private boolean isActive;
	private String user;
}