package com.indekos.dto.request;

import lombok.Data;

@Data
public class SubscriptionClientRequest extends AuditableRequest {
    private String endPoint;

    private String publicKey;

    private String auth;

}
