package com.indekos.dto.request;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.Getter;

@Data
public class AnnouncementRequest extends AuditableRequest {
	
	@NotBlank(message = "title name is required")
	private String title;
	
	@NotBlank(message = "description name is required")
	private String description;
	
	@NotBlank(message = "period name is required")
	private String period;
	
	private MultipartFile image;
}