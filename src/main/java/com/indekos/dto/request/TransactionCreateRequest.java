package com.indekos.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class TransactionCreateRequest extends AuditableRequest{
    private List<String> taskItemIds;
    private List<String> rentItemIds;
}
