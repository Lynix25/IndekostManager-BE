package com.indekos.model;

import com.indekos.common.base.entity.AuditableEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor @NoArgsConstructor
public class Transaction extends AuditableEntity {
    private Long penaltyFee;
    private String paymentType;
    private String paymentStatus;

    @OneToMany(targetEntity = Service.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_id", referencedColumnName = "id")
    private List<Service> serviceItem;

    @OneToMany(targetEntity = Rent.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_id", referencedColumnName = "id")
    private List<Rent> rentItem;
}
