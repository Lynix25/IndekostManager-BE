package com.indekos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

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
@Table(name = "attachment")
public class Attachment extends AuditableEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Column(nullable = false, columnDefinition = "text")
	private String bucket;
	
	@Column(nullable = false, columnDefinition = "text")
	private String filePath;
	
	@Column(nullable = false, columnDefinition = "text")
	private String name;
	
	@Column(nullable = false)
	private String extension;
	
	@Column(nullable = false)
	private String contentType;
	
	@Column(nullable = false)
	private String contentDisposition;
	
	@Column(nullable = false, columnDefinition = "bigint")
	private Long byteSize;
	
	@Column(nullable = false)
	private String entity;
	
	@Column(nullable = false)
	private String entityId;
	
	@Transient
	private String previewUrl;
	
	@Transient
	private String downloadUrl;
}
