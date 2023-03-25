package com.indekos.dto.response;

import java.util.List;

import com.indekos.model.ContactAblePerson;
import com.indekos.model.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserResponse {
	
	private User user;
	private List<UserDocumentResponse> userDocuments;
}
