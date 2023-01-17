package com.indekos.model;


import com.indekos.common.base.entity.AuditableEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "task")
public class Task extends AuditableEntity {
    private String service_id;
    private Long service_date;
    private String summary;
    private String notes;

    private int status;
}
