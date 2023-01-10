package com.indekos.model;

import java.time.Instant;

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
@Table(name = "user")
public class User extends AuditableEntity {
    
	private static final long serialVersionUID = 1L;
	
	@Column(nullable = false)
    private String name;
	
	@Column(nullable = false)
    private String alias;
	
	@Column(nullable = false)
    private String email;
	
	@Column(nullable = false)
    private String phone;
	
	@Column(nullable = false)
    private String job;
	
	@Column(nullable = false)
    private String gender;
	
	@Column(columnDefinition = "text")
    private String description;
	
	@Column(nullable = false)
	private Instant joinedOn;
	
	@Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isDeleted;
    
	private Instant inactiveSince;
	
	@Column(nullable = false)
    private String roleId;
    
    private String roomId;
    private String accountId;
}
