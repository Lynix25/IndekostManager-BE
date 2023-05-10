package com.indekos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.indekos.common.base.entity.AuditableEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@AllArgsConstructor @NoArgsConstructor
public class Transaction extends AuditableEntity {
	private static final long serialVersionUID = 1L;
	
	private Long penaltyFee;
    private String token;

    private String paymentId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL)
    private List<Task> taskItems;

    @OneToMany(mappedBy = "transaction" ,cascade = CascadeType.ALL)
    private List<Rent> rentItems;
}
