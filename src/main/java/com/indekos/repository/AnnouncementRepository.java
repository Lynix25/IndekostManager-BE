package com.indekos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.indekos.model.Announcement;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, String> {
	
	@Query(value="SELECT * FROM announcement WHERE is_active IS TRUE "
				+ "ORDER BY created_date DESC LIMIT :limit_size", nativeQuery = true)
	List<Announcement> getTopAnnouncement(@Param("limit_size") int limit);
	
	@Query(value="SELECT * FROM announcement WHERE is_active IS TRUE AND "
				+ "(title LIKE %:keyword% OR description LIKE %:keyword%) "
				+ "ORDER BY created_date DESC", nativeQuery = true)
	List<Announcement> findAllActiveAnnouncement(@Param("keyword") String keyword);
	
	@Query(value = "SELECT * FROM announcement WHERE title LIKE :title", nativeQuery = true)
	Announcement findByTitle(@Param("title") String title);
	
	@Query(value = "SELECT * FROM announcement WHERE title LIKE :title AND id NOT LIKE :announcement_id", nativeQuery = true)
	Announcement findByTitleAndIdNot(@Param("title") String title, @Param("announcement_id") String announcementId);
}
