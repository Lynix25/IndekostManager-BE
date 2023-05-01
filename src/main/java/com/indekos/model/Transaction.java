package com.indekos.model;

import com.indekos.common.base.entity.AuditableEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@AllArgsConstructor @NoArgsConstructor
public class Transaction extends AuditableEntity {
	private static final long serialVersionUID = 1L;
	
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
