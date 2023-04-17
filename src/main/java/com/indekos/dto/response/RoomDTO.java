package com.indekos.dto.response;

import com.indekos.model.Room;

import lombok.Data;

@Data
public class RoomDTO {
	
	private Room room;
	private Integer totalTenants;
	private String status;
}
