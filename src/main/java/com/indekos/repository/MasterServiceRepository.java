package com.indekos.repository;

import com.indekos.model.MasterService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterServiceRepository extends JpaRepository<MasterService, String> {
}
