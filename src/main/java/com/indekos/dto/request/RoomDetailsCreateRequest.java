package com.indekos.dto.request;

import lombok.Getter;

@Getter
public class RoomDetailsCreateRequest extends AuditableRequest {
	private String name;
    private String description;
    private String category;
}
