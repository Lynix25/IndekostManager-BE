package com.indekos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.indekos.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	
	@Query(value="SELECT * FROM user WHERE is_deleted IS FALSE ORDER BY name ASC", nativeQuery = true)
	List<User> findAllActiveUserOrderByName();
	
    User findByName(String name);
    
    List<User> findByRoleId(String roleId);
}
