package com.indekos.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserDocumentResponse {
	
	private String id;
	private String name;
	private byte[] identityCardImage;
}
