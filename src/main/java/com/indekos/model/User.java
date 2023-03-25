package com.indekos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.indekos.common.base.entity.AuditableEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@AllArgsConstructor @NoArgsConstructor
public class User extends AuditableEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Column(nullable = false)
    private String name;
	
	/* Nama panggilan */
	private String alias;
	
	private String email;
	
	@Column(nullable = false)
    private String phone;
	
	@Column(nullable = false)
    private String job;
	
	@Column(nullable = false)
    private String gender;
	
	/* Status pernikahan apabila ingin sekamar dengan pasangan */
	@Column(nullable = false, columnDefinition = "boolean default false")
    private boolean married;
	
	@Column(columnDefinition = "text")
    private String description;
	
	/* Bergabung ke kos */
	@Column(nullable = false)
	private Long joinedOn;
	
	/* Jika tidak tinggal di kos lagi */
	private Long inactiveSince;
	
	@Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isDeleted;
	
	@ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private MasterRole role;
	
	@Transient
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<UserDocument> userDocument;
	
	@Transient
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<ContactAblePerson> contactAblePerson;

	/* Settings */
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private UserSetting userSetting;
	
	@ManyToOne
	@JoinColumn(name="room_id", referencedColumnName = "id")
	private Room room;

	@JsonIgnore
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Account account;
}