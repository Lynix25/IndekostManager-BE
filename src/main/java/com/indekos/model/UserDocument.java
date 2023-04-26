package com.indekos.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.indekos.common.base.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@NoArgsConstructor @AllArgsConstructor
public class UserDocument extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	@Column
	private String name;
	
	@Lob
	@Column(length = 1000)
	private byte[] image;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}