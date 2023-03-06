package com.indekos.dto.response;

import lombok.Data;

@Data
public class ImageGetResponse {
    private String name;
    private byte[] image;
}
