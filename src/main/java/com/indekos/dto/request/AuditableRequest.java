package com.indekos.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class AuditableRequest {
    @NotEmpty(message = "User ID requester is required")
    private String requesterIdUser;
}
