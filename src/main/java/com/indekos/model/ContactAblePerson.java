package com.indekos.model;

import com.indekos.common.base.entity.BaseEntity;

import javax.persistence.Column;

public class ContactAblePerson extends BaseEntity {
	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String phone;

	@Column(nullable = false)
	private String address;

	@Column(nullable = false)
	private String relation ;
}
