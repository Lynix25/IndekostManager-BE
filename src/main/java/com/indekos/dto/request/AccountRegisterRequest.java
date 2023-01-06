package com.indekos.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AccountRegisterRequest extends Request{
    @NotEmpty(message = "user who create is required")
    @JsonProperty(value = "created_by")
    private String createdBy;

    @NotEmpty(message = "user who edit is required")
    @JsonProperty(value = "last_modified_by")
    private String lastModifiedBy;

}
