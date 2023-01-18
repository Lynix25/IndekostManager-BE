package com.indekos.dto.request;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Data
public class AuditableRequest {
    @NotEmpty(message = "User ID requester is required")
    private String requesterIdUser;
}
