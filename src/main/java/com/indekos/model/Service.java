package com.indekos.model;

import com.indekos.common.base.entity.AuditableEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor @NoArgsConstructor
public class Service extends AuditableEntity {
    @Column(nullable = false)
    private String serviceName;
    @Column(nullable = false)
    private String variant;
    private Integer price;

//    @ManyToOne(targetEntity = Transaction.class, cascade = CascadeType.ALL)
//    @JoinColumn(name = "transaction_id", referencedColumnName = "id")
//    private Transaction transaction;
}
