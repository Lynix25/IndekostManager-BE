package com.indekos.dto;

import com.indekos.common.base.entity.AuditableEntity;
import com.indekos.dto.response.MidtransCheckTransactionResponse;
import com.indekos.model.Rent;
import com.indekos.model.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor
public class TransactionDetailsDTO{
    private String id;
    private Long createdDate;
    private Long lastModifiedDate;
    private Long penaltyFee;
    private String token;
    private MidtransCheckTransactionResponse payment;
    private List<Task> taskItems;
    private List<Rent> rentItems;
    private Integer totalItem;
    private Long totalPrice;
}
