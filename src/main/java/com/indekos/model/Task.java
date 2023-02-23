package com.indekos.model;

import com.indekos.common.base.entity.AuditableEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
@AllArgsConstructor @NoArgsConstructor
public class Task extends AuditableEntity {
    @Column(nullable = false)
    private Long taskDate;
    @Column(nullable = false)
    private String serviceId;
    private String summary;
    private String notes;
    private String requestedBy;
    private Long finishDate;
    /**
     * Status state
     *
     * -1 rejected
     * 0 submited
     * 1 approved
     * 2 on prosses
     * 3 completed
     */
    @Column(nullable = false)
    private Integer status;
}
