package com.indekos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.indekos.common.base.entity.AuditableEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@AllArgsConstructor @NoArgsConstructor
public class MasterRole extends AuditableEntity {
	
	private static final long serialVersionUID = 1L;
	
	// OWNER, ADMIN, TENANT
	@Column(unique = true, nullable = false)
	private String name;
	
	@Column(columnDefinition = "text")
	private String description;
	
	@JsonIgnore
	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<User> user;
}
