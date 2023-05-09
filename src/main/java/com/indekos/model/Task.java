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
@AllArgsConstructor @NoArgsConstructor
public class Task extends AuditableEntity {
	private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name="service_id", referencedColumnName = "id")
    private Service service;

	@Column(nullable = false)
    private Long taskDate;
    
	private String summary;
	private String notes;
	private Long finishDate;
    /**
     * Status state
     *
     * 1. REJECTED
     * 2. SUBMITTED
     * 3. ON PROCESS
     * 4. COMPLETED
     */
    @Column(nullable = false)
    private String status;
    
    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer charge;
    
    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer additionalCharge;
    
    @Column(columnDefinition = "int default 0")
    private Integer requestedQuantity;
    
    private Long dueDate;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName ="id")
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "transaction_id", referencedColumnName = "id")
    private Transaction transaction;
}