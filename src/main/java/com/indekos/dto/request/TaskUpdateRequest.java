package com.indekos.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class TaskUpdateRequest extends AuditableRequest {
    @NotNull(message = "status cannot be empty")
    private Integer status;
}
