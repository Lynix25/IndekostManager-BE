package com.indekos.dto;

import com.indekos.model.UserSetting;
import lombok.Data;

@Data
public class SimpleUserDTO {
	
	private String userName;
	private UserSetting userSetting;
	
	private String roomId;
	private String roomName;
}
