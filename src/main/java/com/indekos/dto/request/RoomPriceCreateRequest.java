package com.indekos.dto.request;

import lombok.Getter;

@Getter
public class RoomPriceCreateRequest extends AuditableRequest {
    private Integer capacity;
    private Integer price;
}
