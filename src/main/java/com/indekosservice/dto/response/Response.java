package com.indekosservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class Response {
//    @JsonProperty()
//    private String errorCode;
    @JsonProperty(value = "error_schema")
    private String errorSchema;
    @JsonProperty(value = "output_schema")
    private String outputSchema;
}
