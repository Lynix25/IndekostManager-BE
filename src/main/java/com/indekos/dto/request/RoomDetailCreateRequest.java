package com.indekos.dto.request;

import lombok.Getter;

@Getter
public class RoomDetailCreateRequest extends AuditableRequest {
    private Integer capacity;
    private Integer price;
}
