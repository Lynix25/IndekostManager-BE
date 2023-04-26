package com.indekos.dto.request;

import lombok.Getter;

@Getter
public class TaskCreateRequest extends AuditableRequest {
    private String variant;
    private String summary;
    private String notes;
    private Long taskDate;
    private String serviceId;
}
