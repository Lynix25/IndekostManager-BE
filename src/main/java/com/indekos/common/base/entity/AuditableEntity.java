package com.indekos.common.base.entity;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners({AuditingEntityListener.class})
@MappedSuperclass
@Data
public class AuditableEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	@CreatedBy
	@Column(nullable = false, updatable = false)
	private String createdBy;

	@CreatedDate
	@Column(nullable = false, updatable = false)
	final private Instant createdDate;

	@LastModifiedBy
	@Column(nullable = false)
	private String lastModifiedBy;

	@LastModifiedDate
	@Column(nullable = false)
	private Instant lastModifiedDate;

	public AuditableEntity(){
		this.createdDate = Instant.now();
		this.lastModifiedDate = Instant.now();
	}

//	public String getCreatedBy() {
//		return createdBy;
//	}
//
//	public void setCreatedBy(String createdBy) {
//		this.createdBy = createdBy;
//	}
//
//	public Instant getCreatedDate() {
//		return createdDate;
//	}
//
//	public void setCreatedDate(Instant createdDate) {
//		this.createdDate = createdDate;
//	}
//
//	public String getLastModifiedBy() {
//		return lastModifiedBy;
//	}
//
//	public void setLastModifiedBy(String lastModifiedBy) {
//		this.lastModifiedBy = lastModifiedBy;
//	}
//
//	public Instant getLastModifiedDate() {
//		return lastModifiedDate;
//	}
//
//	public void setLastModifiedDate(Instant lastModifiedDate) {
//		this.lastModifiedDate = lastModifiedDate;
//	}
//
//	public void updateCreated (String user) {
//		if(user == "" || user == null)
//			setCreatedBy("system");
//		else
//			setCreatedBy(user);
//		setCreatedDate(Instant.now());
//	}
	
	public void updateLastModified (String user) {
		if(user == "" || user == null)
			setLastModifiedBy("system");
		else
			setLastModifiedBy(user);
		setLastModifiedDate(Instant.now());
	}

	private void setLastModifiedDate(Instant instant) {
		this.lastModifiedDate = instant;
	}


}