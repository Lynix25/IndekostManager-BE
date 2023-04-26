package com.indekos.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AuditableRequest {
    @NotEmpty(message = "User ID requester is required")
    private String requesterId;
}
