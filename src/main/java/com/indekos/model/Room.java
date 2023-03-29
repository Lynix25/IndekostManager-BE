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
public class Room extends AuditableEntity {
	private static final long serialVersionUID = 1L;
	@Column(nullable = false)
    private String name;
	
	@Column(columnDefinition = "text")
    private String description;
	
	@Column(nullable = false, columnDefinition = "int default 0")
	private Integer quota;
	
	@Column(nullable = false, columnDefinition = "boolean default false")
	private boolean isDeleted;

	@Column(nullable = false)
	private Integer floor;

	@Column(nullable = false)
	private String allotment;

//	@Column(nullable = false)
//	private String facility;

	@OneToMany(targetEntity = RoomPriceDetail.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "room_id",referencedColumnName = "id")
	private List<RoomPriceDetail> prices;

	@OneToMany(targetEntity = RoomDetail.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "room_id",referencedColumnName = "id")
	private List<RoomDetail> details;

	@OneToMany(targetEntity = User.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "room_id", referencedColumnName = "id")
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "room")
	private List<User> users;

//	private List<String> rules;
//	private List<String> specification;
//	private List<String> bathRoomFacilities;

	public boolean hasUser(User user){
		for (User u: users) {
			if(u.getId().equals(user.getId())) return true;
		}
		return false;
	}
}

