package com.indekos.dto.response;

import com.indekos.model.Rent;
import com.indekos.model.Task;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class TransactionCreateResponse {
    List<Task> tasks;
    List<Rent> rents;
    String token;
}
