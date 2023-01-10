package com.indekos.dto.request;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class RoomRequest {
	
	private String name;
	private String description;
	private Integer quota;
	private String user;
}
