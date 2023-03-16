package com.indekos.model;

import com.indekos.common.base.entity.AuditableEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor @NoArgsConstructor
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
	private Long joinedOn;
	
	@Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isDeleted;
    
	private Long inactiveSince;
	
	@Column(nullable = false)
    private String roleId;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="room_id")
	private Room room;

//  private String accountId;
//	@OneToOne(targetEntity = Account.class, cascade = CascadeType.ALL)
//	@JoinColumn(name = "account_id", referencedColumnName = "id")
//	private Account account;

//	@ManyToOne(targetEntity = Room.class, cascade = CascadeType.ALL)
//	@JoinColumn(name = "user_id", referencedColumnName = "id")
//	private Room room;
	@OneToMany(targetEntity = ContactAblePerson.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private List<ContactAblePerson> contactAblePeople;

	@Lob
	@Column(nullable = false, length = 1000)
	private byte[] identityCardImage;

	//Settings
	private boolean shareRoom;
	private boolean enableNotification;

}
