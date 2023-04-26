package com.indekos.controller.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.indekos.model.User;
import com.indekos.model.UserDocument;

public interface UserDocumentRepository extends JpaRepository<UserDocument, String> {
	List<UserDocument> findByUser(User user);
}
