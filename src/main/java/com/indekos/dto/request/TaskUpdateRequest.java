package com.indekos.dto.request;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Data
public class TaskUpdateRequest extends AuditableRequest {
    @NotNull(message = "status cannot be empty")
    private String status;
    private String notes;
    private Integer additionalCharge;
    private Integer requestedQuantity;
}
