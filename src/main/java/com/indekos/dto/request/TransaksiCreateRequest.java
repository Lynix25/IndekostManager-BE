package com.indekos.dto.request;

import lombok.Data;
import lombok.Getter;

@Getter
public class TransaksiCreateRequest extends AuditableRequest{
    private String category;
    private String description;
}
