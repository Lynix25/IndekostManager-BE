package com.indekos.model;

import com.indekos.common.base.entity.AuditableEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@AllArgsConstructor @NoArgsConstructor
public class Task extends AuditableEntity {

	private static final long serialVersionUID = 1L;
	
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
