package com.indekos.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class RoomPriceCreateRequest extends AuditableRequest {
    private Integer capacity;
    private Integer price;
}
