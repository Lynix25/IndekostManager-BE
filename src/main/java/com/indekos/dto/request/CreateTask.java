package com.indekos.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class CreateTask {
    private String variant;
    private String summary;
    private String notes;
    private Long dateTime;
    private String createdBy;
}
