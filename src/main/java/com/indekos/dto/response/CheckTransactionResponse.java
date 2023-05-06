package com.indekos.dto.response;

import com.indekos.dto.TaskDetailDTO;
import com.indekos.model.Rent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor
public class CheckTransactionResponse {
    private List<TaskDetailDTO> taskItem;
    private List<Rent> rentItem;
    private Long unpaidTotal;
    private Long maxDueDate;
}
