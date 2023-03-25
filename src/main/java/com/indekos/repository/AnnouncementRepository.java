package com.indekos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.indekos.model.Announcement;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, String> {
	
	List<Announcement> findAllByOrderByCreatedDateDesc();
	Announcement findByTitle(String title);
	Announcement findByTitleAndIdNot(String title, String id);
}