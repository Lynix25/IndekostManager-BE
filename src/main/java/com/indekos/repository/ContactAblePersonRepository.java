package com.indekos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.indekos.model.ContactAblePerson;
import com.indekos.model.User;

public interface ContactAblePersonRepository extends JpaRepository<ContactAblePerson, String>{
	ContactAblePerson findByUserAndId(User user, String id);
	List<ContactAblePerson> findByUserAndIsDeleted(User user, boolean isDeleted);
}
