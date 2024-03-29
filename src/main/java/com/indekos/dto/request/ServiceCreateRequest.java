package com.indekos.dto.request;

import lombok.Getter;

@Getter
public class ServiceCreateRequest extends AuditableRequest {
    private String serviceName;
    private String variant;
    private Integer price;
    private Integer quantity;
    private String units;
    private Integer dueDate;
    private Integer penalty;
}
