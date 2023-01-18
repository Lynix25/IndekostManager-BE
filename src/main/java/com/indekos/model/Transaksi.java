package com.indekos.model;

import com.indekos.common.base.entity.AuditableEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@Entity
@AllArgsConstructor @NoArgsConstructor
public class Transaksi extends AuditableEntity {
    private Long cratedAt;
    private Long resolveAt;
    private String requesterUser;
    private String category;
    private String description;
}
