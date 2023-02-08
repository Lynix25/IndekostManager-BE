package com.indekos.dto.request;

import com.indekos.model.Rent;
import com.indekos.model.Service;
import lombok.Getter;

import java.util.List;

@Getter
public class TransactionCreateRequest extends AuditableRequest{
    private List<Service> serviceItem;
    private List<Rent> rentItem;
}
