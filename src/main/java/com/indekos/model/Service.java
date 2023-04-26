package com.indekos.model;

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
public class Service extends AuditableEntity {
    
	private static final long serialVersionUID = 1L;
	
	@Column(nullable = false)
    private String serviceName;
    
	@Column(nullable = false)
    private String variant;
    
	private Integer price;
}
