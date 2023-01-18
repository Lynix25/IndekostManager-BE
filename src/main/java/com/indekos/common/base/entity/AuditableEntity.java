package com.indekos.common.base.entity;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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

//	public void updateCreated (String user) {
//		if(user == "" || user == null)
//			setCreatedBy("system");
//		else
//			setCreatedBy(user);
//		setCreatedDate(Instant.now());
//	}
	
	public void updateLastModified (String user) {
		if(user == "" || user == null)
//			setLastModifiedBy("system");
			this.lastModifiedBy = "system";
		else
//			setLastModifiedBy(user);
			this.lastModifiedBy = "user";
		setLastModifiedDate();
	}

	private void setLastModifiedDate() {
		this.lastModifiedDate = Instant.now();
	}

	public void create(String userUpdaterId){
		this.createdBy = userUpdaterId;
		this.lastModifiedBy = userUpdaterId;
	}

	public void update(String userUpdaterId){
		this.lastModifiedDate = Instant.now();
		this.lastModifiedBy = userUpdaterId;
	}

}