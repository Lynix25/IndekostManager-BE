package com.indekos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.indekos.model.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, String> {
	
}
