package com.indekos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.indekos.common.base.entity.AuditableEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@NoArgsConstructor @AllArgsConstructor
public class Rent extends AuditableEntity {
	private static final long serialVersionUID = 1L;
    private Integer price;
    private String month;
    private Long dueDate;
    private Integer status;
    private String roomId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "transaction_id", referencedColumnName = "id")
    private Transaction transaction;
}
