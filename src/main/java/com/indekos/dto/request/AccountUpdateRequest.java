package com.indekos.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AccountUpdateRequest {
    @JsonProperty(value = "user_id")
    private String userID;
    @JsonProperty(value = "account_id")
    private String accountID;
}
