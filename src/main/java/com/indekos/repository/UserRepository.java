package com.indekos.repository;

import com.indekos.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	
	@Query(value="SELECT * FROM user WHERE is_deleted IS FALSE ORDER BY name ASC", nativeQuery = true)
	List<User> findAllActiveUserOrderByName();

//		@Query(value="SELECT * FROM announcement WHERE "
//				+ "title LIKE %:keyword% OR description LIKE %:keyword% "
//				+ "ORDER BY created_date DESC", nativeQuery = true)
//	List<Announcement> findSearchAnnouncement(@Param("keyword") String keyword);
//
	@Query(value = "SELECT * FROM user WHERE room_id LIKE :room_id", nativeQuery = true)
	List<User> findAllByRoomId(@Param("room_id") String keyword);

//    User findByAccountId(String accountId);

    List<User> findByRoleId(String roleId);
}
