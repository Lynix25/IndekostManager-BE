package com.indekos.repository;

import com.indekos.model.Room;
import com.indekos.model.RoomPriceDetail;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoomPriceDetailRepository extends JpaRepository<RoomPriceDetail, Long> {
	
	@Query(value = "SELECT * FROM room_price_detail WHERE room_id = :room_id", nativeQuery = true)
	RoomPriceDetail getPriceDetailByRoomId(@Param("room_id") String roomId);
	
	RoomPriceDetail findByRoomAndId(Room room, Long id);
	List<RoomPriceDetail> findByRoom(Room room);
}
