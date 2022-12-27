package com.indekos.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class TokenSessionResponse {
    @JsonProperty(value = "token_id")
    private String tokenID;
    private long expired_date;
}
