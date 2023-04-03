package com.indekos.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class TokenSessionResponse {
    private String tokenId;
    private int expiresIn;
}
