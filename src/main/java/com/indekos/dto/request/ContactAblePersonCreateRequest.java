package com.indekos.dto.request;

import lombok.Getter;

@Getter
public class ContactAblePersonCreateRequest {
	private String name;
    private String phone;
    private String address;
    private String relation ;
}
