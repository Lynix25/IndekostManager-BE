package com.indekos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@AllArgsConstructor @NoArgsConstructor
public class RoomDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	private String description;
	
	@Column(nullable = false, columnDefinition = "boolean default true")
	private boolean enable;
	
	@ManyToOne
	@JoinColumn(name = "detail_category_id", referencedColumnName = "id")
    private MasterRoomDetailCategory masterRoomDetailCategory;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;

	public RoomDetail(String name, MasterRoomDetailCategory masterRoomDetailCategory, Room room) {
		super();
		this.name = name;
		this.masterRoomDetailCategory = masterRoomDetailCategory;
		this.room = room;
		this.enable = true;
	}
}
