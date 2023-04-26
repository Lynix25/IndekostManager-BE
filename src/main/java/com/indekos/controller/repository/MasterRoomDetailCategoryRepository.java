package com.indekos.controller.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.indekos.model.MasterRoomDetailCategory;

public interface MasterRoomDetailCategoryRepository extends JpaRepository<MasterRoomDetailCategory, Long> {
	
	MasterRoomDetailCategory findByName(String name);
	List<MasterRoomDetailCategory> findAllByOrderByIdAsc();
}
