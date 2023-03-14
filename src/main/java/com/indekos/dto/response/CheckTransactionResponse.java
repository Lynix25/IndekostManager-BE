package com.indekos.dto.response;

import com.indekos.model.Rent;
import com.indekos.model.Service;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor
public class CheckTransactionResponse {
    private List<Service> serviceItem;
    private List<Rent> rentItem;
    private Long unpaidTotal;
    private Long maxDueDate;
}
