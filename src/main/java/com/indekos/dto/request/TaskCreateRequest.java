package com.indekos.dto.request;

import lombok.Getter;

@Getter
public class TaskCreateRequest extends AuditableRequest {
    private String summary;
    private Long taskDate;
    private String serviceId;
    private Integer charge;
    private Integer requestedQuantity;
}
