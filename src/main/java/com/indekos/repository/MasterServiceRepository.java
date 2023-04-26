package com.indekos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.indekos.model.MasterService;

@Repository
public interface MasterServiceRepository extends JpaRepository<MasterService, Long> {
	List<MasterService> findAllByOrderByIdAsc();
}
