package com.indekos.controller.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.indekos.model.ContactAblePerson;
import com.indekos.model.User;

public interface ContactAblePersonRepository extends JpaRepository<ContactAblePerson, String>{
	ContactAblePerson findByUserAndId(User user, String id);
	
	@Query(value = "SELECT * FROM contact_able_person WHERE is_deleted IS FALSE AND user_id = :user_id AND id LIKE %:id% ORDER BY name ASC", nativeQuery = true)
	List<ContactAblePerson> findActiveContactable(@Param("user_id") String userId, @Param("id") String id);
}
