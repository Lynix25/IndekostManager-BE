package com.indekos.controller.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.indekos.model.MasterRole;
import com.indekos.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	
	@Query(value="SELECT * FROM user WHERE is_deleted IS FALSE ORDER BY name ASC", nativeQuery = true)
	List<User> findAllActiveUserOrderByName();

	@Query(value = "SELECT * FROM user WHERE is_deleted IS FALSE AND room_id LIKE :room_id", nativeQuery = true)
	List<User> findAllByRoomId(@Param("room_id") String roomId);
    List<User> findByRole(MasterRole role);
}
