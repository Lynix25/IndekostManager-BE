package com.indekos.common.base.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@EntityListeners({AuditingEntityListener.class})
@MappedSuperclass
@Getter
public class AuditableEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	@CreatedBy
	@Column(nullable = false, updatable = false)
	private String createdBy;

	@CreatedDate
	@Column(nullable = false, updatable = false)
	final private Long createdDate;

	@LastModifiedBy
	@Column(nullable = false)
	private String lastModifiedBy;

	@LastModifiedDate
	@Column(nullable = false)
	private Long lastModifiedDate;

	public AuditableEntity(){
		this.createdDate = System.currentTimeMillis();
		this.lastModifiedDate = System.currentTimeMillis();
	}

	public void create(String userUpdaterId){
		this.createdBy = userUpdaterId;
		this.lastModifiedBy = userUpdaterId;
	}

	public void update(String userUpdaterId){
		this.lastModifiedDate = System.currentTimeMillis();
		this.lastModifiedBy = userUpdaterId;
	}
}