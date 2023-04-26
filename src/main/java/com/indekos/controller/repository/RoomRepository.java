package com.indekos.controller.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.indekos.dto.response.AvailableRoomResponse;
import com.indekos.model.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {
	
	@Query(value="SELECT * FROM room WHERE is_deleted IS FALSE ORDER BY name ASC", nativeQuery = true)
	List<Room> findAllByOrderByNameAsc();
	
	Room findByName(String roomName);
	
	@Query(value = "SELECT * FROM room WHERE name LIKE :room_name AND id NOT LIKE :room_id AND is_deleted IS FALSE", nativeQuery = true)
    Room findByNameAndIdNot(@Param("room_name") String roomName, @Param("room_id")  String roomId);
	
	@Query(value="SELECT id, name, description, quota, (quota - "
			+ "(SELECT COUNT(*) FROM user u LEFT JOIN room r "
			+ "ON u.room_id = r.id WHERE r.is_deleted IS FALSE "
			+ "AND u.is_deleted IS FALSE)) AS remainingQuota "
			+ "FROM room WHERE name LIKE %:keyword% AND is_deleted IS FALSE "
			+ "HAVING remainingQuota > 0 ORDER BY name ASC", nativeQuery = true)
	List<AvailableRoomResponse> findAllAvailableRoom(@Param("keyword") String keyword);
	
	@Query(value = "SELECT COUNT(*) FROM user WHERE is_deleted IS FALSE AND room_id = :room_id", nativeQuery = true)
	int countCurrentTenantsOfRoom(@Param("room_id") String roomId);
	
	@Query(value = "SELECT COUNT(*) FROM user u JOIN user_setting us ON u.id = us.user_id "
			+ "WHERE is_deleted IS FALSE AND share_room IS TRUE AND room_id = :room_id", nativeQuery = true)
	int checkIfRoomIsShared(@Param("room_id") String roomId);
}