package com.indekos.model;

import com.indekos.common.base.entity.AuditableEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@Entity
@AllArgsConstructor @NoArgsConstructor
public class Task extends AuditableEntity {
    private Long taskDate;
    private String summary;
    private String notes;
    private int status;
}
