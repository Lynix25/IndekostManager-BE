package com.indekos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.indekos.model.Announcement;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, String> {
	
	@Query(value="SELECT * FROM announcement "
				+ "ORDER BY created_date DESC LIMIT :limit_size", nativeQuery = true)
	List<Announcement> getTopAnnouncement(@Param("limit_size") int limit);
	
	@Query(value="SELECT * FROM announcement WHERE "
				+ "title LIKE %:keyword% OR description LIKE %:keyword% "
				+ "ORDER BY created_date DESC", nativeQuery = true)
	List<Announcement> findSearchAnnouncement(@Param("keyword") String keyword);
	
	List<Announcement> findAllByOrderByCreatedDateDesc();
	Announcement findByTitle(String title);
	Announcement findByTitleAndIdNot(String title, String id);
}