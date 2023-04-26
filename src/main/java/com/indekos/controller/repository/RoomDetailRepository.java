package com.indekos.controller.repository;

import com.indekos.model.Room;
import com.indekos.model.RoomDetail;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomDetailRepository extends JpaRepository<RoomDetail, Long> {
	
	RoomDetail findByRoomAndId(Room room, Long id);
	List<RoomDetail> findByRoom(Room room);
}
