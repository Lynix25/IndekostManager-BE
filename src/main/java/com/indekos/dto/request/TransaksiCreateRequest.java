package com.indekos.dto.request;

import lombok.Data;

@Data
public class TransaksiCreateRequest extends Request{
    private String category;
    private String description;
}
