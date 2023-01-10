package com.indekos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.indekos.common.base.entity.AuditableEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
@Table(name = "room")
public class Room extends AuditableEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Column(unique = true, nullable = false)
    private String name;
	
	@Column(columnDefinition = "text")
    private String description;
	
	@Column(nullable = false, columnDefinition = "int default 0")
	private Integer quota;
	
	@Column(nullable = false, columnDefinition = "boolean default false")
	private boolean isDeleted;
}
