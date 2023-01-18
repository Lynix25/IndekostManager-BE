package com.indekos.dto.request;

import lombok.Getter;

@Getter
public class ServiceCreateRequest extends AuditableRequest {
    private String serviceName;
    private String variant;
    private Integer price;
}
