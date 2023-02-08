package com.indekos.model;

import com.indekos.common.base.entity.AuditableEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor @AllArgsConstructor
public class Rent extends AuditableEntity {
    private String roomId;
    private Integer price;
    private String month;
    private Long dueDate;
    private Integer status;

//    @ManyToOne(targetEntity = Transaction.class, cascade = CascadeType.ALL)
//    @JoinColumn(name = "transaction_id", referencedColumnName = "id")
//    private Transaction transaction;
}
