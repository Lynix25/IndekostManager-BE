package com.indekos.dto.response;

import com.indekos.model.Account;
import com.indekos.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserResponse {
	
	private User user;
	private Account account;
}
