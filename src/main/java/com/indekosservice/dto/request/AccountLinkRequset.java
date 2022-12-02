package com.indekosservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AccountLinkRequset {
    @JsonProperty(value = "user_id")
    private String userID;
    @JsonProperty(value = "account_id")
    private String accountID;
}
