package com.indekos.dto;

import lombok.Data;

@Data
public class SimpleUserDTO {
	
	private String userName;
	private UserSettingsDTO userSetting;
	
	private String roomId;
	private String roomName;
}
