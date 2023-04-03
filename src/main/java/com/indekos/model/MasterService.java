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
public class MasterService extends AuditableEntity {
    
    private static final long serialVersionUID = 1L;
    
	@Column(unique = true, nullable = false)
	private String name;
	
    @Column(columnDefinition = "text")
    private String description;
    
    @Column(nullable = false, columnDefinition = "boolean default false")
	private boolean isDeleted;
}
