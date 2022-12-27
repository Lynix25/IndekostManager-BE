package com.indekos.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserRegisterRequest{
    @JsonProperty(value = "no_telp")
    private String noTelp;
    private String name;
}
