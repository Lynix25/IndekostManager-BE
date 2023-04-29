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
	private String serviceId;

	@Column(nullable = false)
    private Long taskDate;
    
	private String summary;
    
	private String notes;
    
	private Long finishDate;
    /**
     * Status state
     *
     * 1. REJECTED
     * 2. SUBMITEED
     * 3. ACCEPTED
     * 4. ON PROCESS
     * 5. COMPLETED
     */
    @Column(nullable = false)
    private String status;
    
    @Column(columnDefinition = "int default 0")
    private Integer charge;
}
